<%-- 
    Document   : getkhachhang
    Created on : Jul 15, 2016, 10:33:30 PM
    Author     : Thorfinn
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.ttdk.bean.Khachhang"%>

        <%
            String countries[] = {
                            "Afghanistan",
                            "Albania",
                            "Algeria",
                            "Andorra",
                            "Angola",
                            "Antigua and Barbuda",
                            "Argentina",
                            "Armenia",
                            "Yemen",
                            "Zambia",
                            "Zimbabwe"
                            };
 
    String query = (String)request.getParameter("q");
    //System.out.println("1"+request.getParameterNames().nextElement());
    response.setHeader("Content-Type", "text/html");
    int cnt=1;
    for(int i=0;i<countries.length;i++)
    {
        if(countries[i].toUpperCase().startsWith(query.toUpperCase()))
        {
            out.print(countries[i]+"\n");
            if(cnt>=10)
                break;
            cnt++;
        }
    }

        %>