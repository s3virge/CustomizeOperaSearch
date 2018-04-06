import java.io.*;

public class Main {
    /**
     * @kaki87 You can remove them, by following the below steps:
     *
     *     Open opera:about, copy the path to the profile (Paths > Profile), and make a note of the path to
     *     the installation root (Paths > Install), close the browser,
     *
     *     Open your file explorer, and navigate to the installationroot\operaversion\resources subfolder
     *     (eg. installationroot\36.0.2130.46\resources), rename the default_partner_content.json to eg.
     *     default_partner-old, and overwrite the default_partner_content.json with ab_tests.json,
     *
     *     Navigate to the profile folder, and delete the default_partner_content.json, relaunch the browser.
     *     In case, that the above doesn't work with the ab_tests.json from your installation, you can use this one, enjoy!
     */

    private String operaIstallFolder = "/usr/lib/x86_64-linux-gnu/opera/resources";
    private String operaProfileFolder = "/home/s3virge/.config/opera";

    //rename the default_partner_content.json
    private void rename(String fromFile, String toFile){
        String oldFilePath = operaIstallFolder + "/" + fromFile;
        //String oldFilePath = operaIstallFolder + "/default_partner_content.json";
        String newFilePath = operaIstallFolder + "/" + toFile;
        //String newFilePath = operaIstallFolder + "/default_partner_content_OLD.json";

        File oldFile = new File(oldFilePath);
        File newFile = new File(newFilePath);

        if (oldFile.renameTo(newFile)) {
            System.out.println("Rename file was succesful");
        }
        else {
            System.out.println("File rename was failed");
        }
    }

    //overwrite the default_partner_content.json with ab_tests.json,
    private void overwrite(String fromFileName, String toFileName) {
        fromFileName = operaIstallFolder + "/" + fromFileName;
        toFileName = operaIstallFolder + "/" + toFileName;

        InputStream inStream = null;
        OutputStream outStream = null;

        try{
            File afile = new File(fromFileName);
            File bfile = new File(toFileName);

            inStream = new FileInputStream(afile);
            outStream = new FileOutputStream(bfile);

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes
            while ((length = inStream.read(buffer)) > 0){
                outStream.write(buffer, 0, length);
            }

            inStream.close();
            outStream.close();

            System.out.println("File is copied successful!");

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    //and delete the default_partner_content.json
    private void delete(String deleteFilePath) {
        File file = new File(operaProfileFolder + "/" + deleteFilePath);

        if (file.delete()) {
            System.out.println("File " + deleteFilePath + " was successfully deleted");
        }
        else {
            System.out.println("File is failed to delete");
        }
    }

    public static void main(String[] args) {
        Main app = new Main();

        String partnerContentFile = "default_partner_content.json";
        app.rename(partnerContentFile, partnerContentFile + ".OLD");
        app.overwrite("ab_tests.json", partnerContentFile);
        app.delete(partnerContentFile);

        System.out.println("Thet`s it. Relaunch Opera browser.");
    }
}
