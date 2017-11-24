package pt.ulusofona.es.g6.form;


import org.hibernate.validator.constraints.NotEmpty;

import java.io.File;

public class UploadForm {

    public static final String UPLOAD_FOLDER = "upload";

    static{
        new File(UPLOAD_FOLDER).mkdir();
    }

}