package ws.otter.util;

import org.springframework.stereotype.Component;

// check param valid
@Component
public class Check {
    public static Boolean param(Object[] params) {

        for (Integer i = 0; i < params.length; i++) {
            if (params[i] == null) {
                return false;
            }

            switch (params[i].getClass().getSimpleName()) {
                case "String":
                    if ("".equals(params[i])) {
                        return false;
                    }
                    break;

                default:
                    break;
            }
        }

        return true;
    }
}