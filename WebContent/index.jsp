<%

            String serverName = request.getServerName().toLowerCase();
            int serverPort = request.getServerPort();
            response.sendRedirect("http://"
                    + serverName
                    + ((serverPort == 80) ? "" : ":"
                            + String.valueOf(serverPort))
                    + "/jbm/login.do");
%>