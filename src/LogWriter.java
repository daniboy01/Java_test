import java.io.FileWriter;
import java.io.IOException;

public class LogWriter {
    private static LogWriter instance;
    private FileWriter fileWriter;

    private LogWriter() throws IOException {
        this.fileWriter = new FileWriter("application.log", true);
    }

    public static LogWriter getInstance(){
        if (instance == null)
            try {
                instance = new LogWriter();
            } catch (IOException e){
                e.printStackTrace();
            }
        return instance;
    }

    public void Log(String message, String row) throws IOException {
        fileWriter.append(message+" :");
        fileWriter.append(row + "\n");
        fileWriter.flush();
        fileWriter.close();
    }
}
