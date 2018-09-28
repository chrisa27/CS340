package com.example.Test;

/**
 * Created by chrisa27 on 3/9/17.
 */

public class TestDriver {
    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main(
                "com.example.Test.DAOTest.AuthDAOTest",
                "com.example.Test.DAOTest.EventDAOTest",
                "com.example.Test.DAOTest.PersonDAOTest",
                "com.example.Test.DAOTest.UserDAOTest",
                "com.example.Test.ModelTest.EventTest",
                "com.example.Test.ModelTest.PersonTest",
                "com.example.Test.ModelTest.TokenTest",
                "com.example.Test.ModelTest.UserAccountTest",
                "com.example.Test.ServerProxyTest.ClearProxyTest",
                "com.example.Test.ServerProxyTest.EventIDProxyTest",
                "com.example.Test.ServerProxyTest.EventProxyTest",
                "com.example.Test.ServerProxyTest.FillProxyTest",
                "com.example.Test.ServerProxyTest.LoadProxyTest",
                "com.example.Test.ServerProxyTest.LoginProxyTest",
                "com.example.Test.ServerProxyTest.PersonIDProxyTest",
                "com.example.Test.ServerProxyTest.PersonProxyTest",
                "com.example.Test.ServerProxyTest.RegisterProxyTest",
                "com.example.Test.ServiceTest.ClearServiceTest",
                "com.example.Test.ServiceTest.EventIDServiceTest",
                "com.example.Test.ServiceTest.EventServiceTest",
                "com.example.Test.ServiceTest.FillServiceTest",
                "com.example.Test.ServiceTest.LoadServiceTest",
                "com.example.Test.ServiceTest.LoginServiceTest",
                "com.example.Test.ServiceTest.PersonIDServiceTest",
                "com.example.Test.ServiceTest.PersonServiceTest",
                "com.example.Test.ServiceTest.RegisterServiceTest");
    }
}
