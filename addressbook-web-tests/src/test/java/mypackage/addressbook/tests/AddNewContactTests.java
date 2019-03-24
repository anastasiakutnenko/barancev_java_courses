package mypackage.addressbook.tests;

import mypackage.addressbook.model.ContactData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

public class AddNewContactTests extends TestBase {

    @Test
    public void addNewContactTest() throws Exception {
        List<ContactData> before = app.getContactsHelper().getContactList();
        ContactData contact = new ContactData(
                "Anastasia", "Kutnenko",
                "Shevchenka 100", "111-111-111",
                "0923456789", "222-222-222",
                "333-333-333", "anastasya.kutnenko+1@gmail.com",
                "anastasya.kutnenko+2@gmail.com", "anastasya.kutnenko+3@gmail.com");
        app.getNavigationHelper().goToContactCreationPage();
        app.getContactsHelper().createContact(contact);
        app.getNavigationHelper().goToHomePage();
        List<ContactData> after = app.getContactsHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() + 1);
        contact.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
        before.add(contact);
        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(after, before);
    }
}
