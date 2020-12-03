public class DemoParseTillMatch {

    public static void main(String[] args) {

        try {
            // File name to be consumed
            String filePathStr = "./demo-logs.log";

            while (!FileParser.isStringMatch(
                    FileParser.readFromPointer(filePathStr),
                    "moin")) {

                System.out.println("waiting..");
            }

            System.out.println("--------------------------------------------------------");
            System.out.println("Run next step...");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
