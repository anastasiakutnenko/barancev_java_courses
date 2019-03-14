package mypackage.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        app.getNavigationHelper().goToHomePage();
        app.getContactsHelper().selectContact();
        app.getContactsHelper().deleteContact();
        app.getNavigationHelper().acceptBrowserAlert();
    }
}
