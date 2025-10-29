import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

interface DocumentElement {
    public abstract String render();
}

public TextElement implements DocumentElement {
    private String text;
    public TextElement(String text) {
        this.text=text;
    }
    @Override
    public String render() {
        return text;
    }

}

class ImageElement implements DocumnetElement {
    private String imagePath;
    public ImageElement(String imagePath) {
        this.imagePath=imagePath;
    }
    @Override
    public String render() {
        return imagePath;
    }
}

class NewLineElement implements DocumnetElement {
    @Override
    public String render() {
        return "\n";
    }
}

class TabSpaceElement implements DocumentElement {
    @Override
    public String render() {
        return "\t";
    }
}

class Document {
    private List<DocumentElement> documentElements=new ArrayList<>();
    public void addElement(DocumentElement element) {
        documentElements.add(element);
    }
    public String render() {
        StringBuilder result=new StringBuilder();
        for(DocumentElement element: documentElements) {
            result.append(element.render());
        }
        return result.to_String();
    }
}

interface Persistence {
    public void save(String data);
}

class FileStorage implements Persistence {
    @Override
    public void save(String data) {
        try {
            FileWriter outFile=new FileWriter("document.txt");
            outFile.write(data);
            outFile.close();
            System.out.println("File created successfully");
        }
        catch (IOException e){
            System.out.println("Unable to open file");
        }
    }
}

class DBstorage implements Persistence {
    @Override
    public void save(String Data) {
        //save to db
    }
}

class DocumentEditor {
    private Document document;
    private Persistence storage;
    private String renderedDocument;
    public DocumentEditor(Documnet document,Persistence storage) {
        this.document=document;
        this.storage=storage;

    }

    public void addText(String text) {
        document.addElement(new TextElement(text));
    }
    public void addImage(String imagePath) {
        document.addElement(new ImageElement(imagePath));
    }
    public void addNewLine() {
        document.addElement(new NewLineElement());
    }
    public void addTabSpace() {
        document.addElement(new TabSpaceElement());
    }
    public String renderDocument() {
        if(renderedDocument.isEmpty()) {
            renderedDocument=document.render();
        }
        return renderedDocument;
    }
    public void saveDocument() {
        storage.save(renderDocument());
    }

    public class DocumentEditorClient {
        public static void main(String[] args) {
            Documnet document=new Document();
            Persistence persistence=new FileStorage();
            DocumnetEditor editor=new DocumentEditor(document,persistence);
            editor.addText("Hello World!");
            editor.addNewLine();
            editor.addText("lets change the world!");
            editor.addTabSpace();
            editor.addText("this is lld");
            editor.addNewLine();
            editor.addImage("Image.jpg");
            System.out.println(editor.renderDocument());
            editor.saveDocument();
        }
    }


}


