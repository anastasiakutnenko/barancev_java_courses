package mypackage.addressbook.tests;

import mypackage.addressbook.appmanager.ApplicationManager;
import mypackage.addressbook.model.ContactData;
import mypackage.addressbook.model.Contacts;
import mypackage.addressbook.model.GroupData;
import mypackage.addressbook.model.Groups;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class TestBase {
    Logger logger = LoggerFactory.getLogger(TestBase.class);

    protected final static ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
    }

    @BeforeMethod
    public void logTestStart(Method m, Object[] p) {
        logger.info("start test" + m.getName() + " with parameters " + Arrays.asList(p));
    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method m) {
        logger.info("stop test" + m.getName());
    }

    public void verifyGroupListInUI() {
        if (Boolean.getBoolean("verifyUI")) {
            Groups dbGroups = app.db().groups();
            Groups uiGroups = app.group().all();
            assertThat(uiGroups, equalTo(dbGroups.stream()
                    .map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
                    .collect(Collectors.toSet())));
        }
    }
    public void verifyContactListInUI() {
        if (Boolean.getBoolean("verifyUI")) {
            Contacts dbContacts = app.db().contacts();
            Contacts uiContacts = app.contact().all();
            assertThat(uiContacts,equalTo(dbContacts.stream()
            .map((g) -> new ContactData().withId(g.getId()).withFirstName(g.getFirstName())
            .withLastName(g.getLastName()).withEmail1(g.getEmail1()).withEmail2(g.getEmail2())
            .withEmail3(g.getEmail3()).withHomePhone(g.getHomePhone()).withMobilePhone(g.getMobilePhone())
            .withWorkPhone(g.getWorkPhone())).collect(Collectors.toSet())));
        }
    }
}