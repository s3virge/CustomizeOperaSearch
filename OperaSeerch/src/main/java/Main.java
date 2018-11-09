import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


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

public class Main {

    private String operaIstallFolder = "/usr/lib/x86_64-linux-gnu/opera/resources";
    private String operaProfileFolder = "/home/s3virge/.config/opera";
    private String sysDrive = System.getenv("SYSTEMDRIVE");

    private String getSysDrive() {
        return sysDrive;
    }

    private void setOperaIstallFolder(String operaIstallFolder) {
        this.operaIstallFolder = operaIstallFolder + "\\resources";
    }

    private void setOperaProfileFolder(String operaProfileFolder) {
        this.operaProfileFolder = operaProfileFolder;
    }

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
            JOptionPane.showMessageDialog(null, "File " + deleteFilePath + " was successfully deleted");
        }
        else {
            System.out.println("File " + file.getName() + " is failed to delete");
            JOptionPane.showMessageDialog(null, "File " + file.getName() + " is failed to delete");

        }
    }

    private boolean IsOsWindows() {
        File winDir = new File( sysDrive + "\\Windows");
        //if exist %systemdrive%/Windows folder
        if (winDir.isDirectory()){
            //hence we in Windows
            return true;
        }

        //maybe we in linux
        return false;
    }

    private boolean isWindows64() {
        File file = new File(sysDrive + "\\Windows\\SysWOW64");
        return file.isDirectory();
    }

    //get older opera version folder
    private String getOlderFolder(File folder) {
        String folderName = "none";
        HashMap<Integer, String> hmFolders = new HashMap<>();
        File[] folderEntries = folder.listFiles();

        for (File entry : folderEntries) {
            if (entry.isDirectory()) {
                folderName = entry.getName();
                String newFolderName = folderName.replace(".", "");
                try {
                    int iFolderName = Integer.valueOf(newFolderName);
                    hmFolders.put(iFolderName, folderName);
                }
                catch(NumberFormatException ex){
                    //System.out.println("Folder name does not consist of numbers");
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        }

        //find which folder name has a max number
        //needs to compare hashmap values
        Set<Map.Entry<Integer, String>> set = hmFolders.entrySet();
        int maxValue = 0;

        for (Map.Entry<Integer, String> me : set) {
            if (maxValue < me.getKey()) {
                maxValue = me.getKey();
                folderName = me.getValue();
            }
        }

        //System.out.println("max value Folder Name is " + folderName);
        return folderName;
    }

    public static void main(String[] args) {
        Main app = new Main();

        //In which OS we now?
        if (app.IsOsWindows()) {
            String operaProgramFilesPath = "\\Program Files\\Opera";

            if (app.isWindows64()) {
                operaProgramFilesPath = "\\Program Files (x86)\\Opera";
            }

            String operaVersion = app.getOlderFolder(new File(app.getSysDrive() + operaProgramFilesPath));

            app.setOperaIstallFolder(app.getSysDrive() + operaProgramFilesPath + "\\" + operaVersion);
            app.setOperaProfileFolder(System.getenv("APPDATA") + "\\Opera Software\\Opera Stable");
        }

            String partnerContentFile = "default_partner_content.json";
            app.rename(partnerContentFile, partnerContentFile + ".OLD");
            app.overwrite("ab_tests.json", partnerContentFile);
            app.delete(partnerContentFile);

        System.out.println("Thet`s it. Relaunch Opera browser.");
        JOptionPane.showMessageDialog(null, "Thet`s it. Relaunch Opera browser.");
    }
}
