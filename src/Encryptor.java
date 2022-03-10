import java.util.ArrayList;

public class Encryptor
{
    /** A two-dimensional array of single-character strings, instantiated in the constructor */
    private String[][] letterBlock;

    /** The number of rows of letterBlock, set by the constructor */
    private int numRows;

    /** The number of columns of letterBlock, set by the constructor */
    private int numCols;

    /** Constructor*/
    public Encryptor(int r, int c)
    {
        letterBlock = new String[r][c];
        numRows = r;
        numCols = c;
    }

    public String[][] getLetterBlock()
    {
        return letterBlock;
    }

    /** Places a string into letterBlock in row-major order.
     *
     *   @param str  the string to be processed
     *
     *   Postcondition:
     *     if str.length() < numRows * numCols, "A" in each unfilled cell
     *     if str.length() > numRows * numCols, trailing characters are ignored
     */
    public void fillBlock(String str)
    {
        int count = 0;

        for (int i = 0; i < numRows; i++)
        {
            for (int j = 0; j < numCols; j++)
            {
                if (count != str.length())
                {
                    letterBlock[i][j] = str.substring(count, count + 1);
                    count++;
                }else
                {
                    letterBlock[i][j] = "A";
                }
            }
        }
    }
//        for (int i = 0; i < numRows; i++)
//        {
//            for (int j = 0; j < numCols; j++)
//            {
//                if (count >= str.length())
//                {
//                    letterBlock[i][j] = "A";
//                    count++;
//                }else{
//                    letterBlock[i][j] = str.substring(count, count + 1);
//                    count++;
//                }
//            }
//        }
    public void fillBlockColumn(String str)
    {
        int count = 0;

        for (int j = 0; j < numCols; j++)
        {
            for (int i = 0; i < numRows; i++)
            {
                    letterBlock[i][j] = str.substring(count, count + 1);
                    count++;
            }
        }
    }

    public String decryptBlock()
    {
        String temp = "";
        for (int i = 0; i < numRows; i++)
        {
            for (int j = 0; j < numCols; j++)
            {
                temp+= letterBlock[i][j];
            }
        }
        return temp;
    }

    /** Extracts encrypted string from letterBlock in column-major order.
     *
     *   Precondition: letterBlock has been filled
     *
     *   @return the encrypted string from letterBlock
     */
    public String encryptBlock()
    {
        String temp = "";
        for (int j = 0; j < numCols; j++)
        {
            for (int i = 0; i < numRows; i++)
            {
                temp+= letterBlock[i][j];
            }
        }
        return temp;
    }

    /** Encrypts a message.
     *
     *  @param message the string to be encrypted
     *
     *  @return the encrypted message; if message is the empty string, returns the empty string
     */
    public String encryptMessage(String message)
    {
        String temp = "";
        int k = 0;

        for (int i = 0; i < Math.ceil((double) message.length() / (numRows * numCols)); i++)
        {
            if (k + (numRows * numCols) >= message.length())
            {
                fillBlock(message.substring(k));
            }else{
                fillBlock(message.substring(k, k + (numRows * numCols)));
            }
            String encrypt = encryptBlock();
            temp += encrypt;
            k += (numCols * numRows);
        }
        return temp;
    }

    /**  Decrypts an encrypted message. All filler 'A's that may have been
     *   added during encryption will be removed, so this assumes that the
     *   original message (BEFORE it was encrypted) did NOT end in a capital A!
     *
     *   NOTE! When you are decrypting an encrypted message,
     *         be sure that you have initialized your Encryptor object
     *         with the same row/column used to encrypted the message! (i.e.
     *         the “encryption key” that is necessary for successful decryption)
     *         This is outlined in the precondition below.
     *
     *   Precondition: the Encryptor object being used for decryption has been
     *                 initialized with the same number of rows and columns
     *                 as was used for the Encryptor object used for encryption.
     *
     *   @param encryptedMessage  the encrypted message to decrypt
     *
     *   @return  the decrypted, original message (which had been encrypted)
     *
     *   TIP: You are encouraged to create other helper methods as you see fit
     *        (e.g. a method to decrypt each section of the decrypted message,
     *         similar to how encryptBlock was used)
     */
    public String decryptMessage(String encryptedMessage)
    {
        String temp = "";
        String temp2 = "";
        int k = 0;

        for (int i = 0; i < Math.ceil((double) encryptedMessage.length() / (numRows * numCols)); i++)
        {
            if (k + (numRows * numCols) >= encryptedMessage.length())
            {
                fillBlockColumn(encryptedMessage.substring(k));
            }else{
                fillBlockColumn(encryptedMessage.substring(k, k + (numRows * numCols)));
            }
            String decrypt = decryptBlock();
            ArrayList<String> removeA = new ArrayList<String>();
            for (int p = 0; p < decrypt.length(); p++)
            {
                removeA.add(decrypt.substring(p, p + 1));
            }

            while (removeA.get(removeA.size() - 1).equals("A"))
            {
                removeA.remove(removeA.size() - 1);
            }

            for (String e : removeA)
            {
                temp2 += e;
            }

            temp += temp2;

            k += (numCols * numRows);
        }
        return temp2;
    }
}
