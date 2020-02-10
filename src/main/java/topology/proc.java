package topology;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class proc {
    // Returns default route (network gateway) from /proc/net/route

    protected String readDefaultRoute(String procFile) {
        /*
        If line has second field which matches 00000000, third field is the default GW.
        */
        String gateway = new String();
        try {
            Scanner scanner = new Scanner(new File(procFile));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] entries =  line.split("\\s+");
                if (entries[1].contains("00000000")) {
                    gateway = entries[2];
                    break;
                } else {
                    gateway = "00000000";
                }

            }
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
        }
        return(gateway);
    }

    protected String convertDefaultRoute(String defaultRoute) {
        // converts "Gateway" from hex endianess to dotted decimal notation.
        String[] octets = defaultRoute.split("(?<=\\G.{2})");
        return( Integer.parseInt(octets[3], 16) + "." +
                Integer.parseInt(octets[2], 16) + "." +
                Integer.parseInt(octets[1], 16) + "." +
                Integer.parseInt(octets[0], 16));
    }

    public String getDefaultRoute() throws IOException {
        // Getter that returns default route.
        //String defaultHexRoute = readDefaultRoute("/proc/net/route");
        String defaultHexRoute = readDefaultRoute("/Users/adam/work/public/topology/proc-net-route");
        String defaultDecimalRoute = convertDefaultRoute(defaultHexRoute);
        return defaultDecimalRoute;
    }
}
