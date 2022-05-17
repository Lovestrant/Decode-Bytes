public class DecodeBytes {
    public static void main(String[] args) {

     byte[] bytes = new byte[]{
            //100 101
             0x45, 0x00, 0x00, (byte) 0xd2,
             0x16, 0x61, 0x40, 0x00,
             0x40, 0x06, 0x30, (byte) 0x92,
             (byte) 0xc0, (byte) 0xa8, 0x2b, (byte) 0xd3,
             0x33, 0x0f, (byte) 0xd3, (byte) 0xa8

          };

        //Get the first four Bits
        //Convert to 2's compliment and shift by 4 to get the version of IP
        int version = (bytes[0]&0xff) >> 4;
        System.out.println("Version is:" +version);

        //IHL (Internet Header Length)
                        //0100 0101 & 0000 0101
        int iHLInWords = (bytes[0]&0xff & 0b0000101);
        System.out.println("iHLInWords: "+ iHLInWords);

        //Multiply by four to get the Octate from IHLINWORDS
        int iHLinOctate = iHLInWords*4;
        System.out.println("iHLinOctate: "+ iHLinOctate);

        //Or the second byte with 0 then shift 8 times
        //Or the third byte with the second byte and then get the final answer as the total Length
        int totalLength = 0; //0x00000000
        int totalLength1 = totalLength | (bytes[2]&0xff) << 8;
        int totalLengthFinal = totalLength1 | (bytes[3]&0xff);

        System.out.println("TotalLength is: " + totalLengthFinal);

        //Flags and the fragment offset
        //0x40, 0x00  // 0100 0000, 0000 0000
        //First three bits --> reserved bit, do not fragment, more fragments
        //We need to mask...

        boolean doNotFragment = ((bytes[6]&0xff) &0b01000000) >0;
        boolean moreFragments = ((bytes[6]&0xff) & 0b00100000) >0;
        System.out.println("Do Not Fragment: " + doNotFragment);
        System.out.println("moreFragments: " + moreFragments);

        //Fragment Offset

        int fragmentOffset = 0;
        fragmentOffset |= (bytes[6]&0xff & 0b00011111) << 8;
        fragmentOffset |= (bytes[7]&0xff);
        System.out.println("fragmentOffset: "+fragmentOffset);



        int part1 = bytes[12]&0xff;
        int part2 = bytes[13]&0xff;
        int part3 = bytes[14]&0xff;
        int part4 = bytes[15]&0xff;
        String destinationAddress = part1+"."+part2+"."+part3+"."+part4;
        System.out.println("destinationAddress: " +destinationAddress);

        int part11 = bytes[8]&0xff;
        int part22 = bytes[9]&0xff;
        int part33 = bytes[10]&0xff;
        int part44 = bytes[11]&0xff;
        String sourceAddress = part11+"."+part22+"."+part33+"."+part44;
        System.out.println("sourceAddress: " +sourceAddress);



    }
}
