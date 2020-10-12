package it.ermete.mercurio.schedapalestra;

import java.util.ArrayList;
import it.ermete.mercurio.schedapalestra.FileModel;

public class FileController {

    private final FileModel model;

    public FileController() {
        model = new FileModel();
    }

    public ArrayList<FileDaFS> GetElencoExportsPdf() {
        return model.GetElencoExportsPdf();
    }

    public String EliminaFile(String nomeFile) {
        return model.EliminaFile(nomeFile);
    }
}
