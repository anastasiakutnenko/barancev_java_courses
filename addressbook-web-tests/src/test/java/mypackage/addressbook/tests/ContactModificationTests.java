package mypackage.addressbook.tests;

import mypackage.addressbook.model.ContactData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().list().size() == 0) {
            app.goTo().contactCreationPage();
            app.contact().create(new ContactData()
                    .withFirstName("Anastasia").withLastName("Kutnenko")
                    .withAddress("Shevchenka 100").withHomePhone("111-111-111")
                    .withMobilePhone("0923456789").withWorkPhone("222-222-222")
                    .withFax("333-333-333").withEmail1("anastasya.kutnenko+1@gmail.com")
                    .withEmail2("anastasya.kutnenko+2@gmail.com")
                    .withEmail3("anastasya.kutnenko+3@gmail.com"));
        }
    }

    @Test
    public void testContactModification() {
        app.goTo().homePage();
        List<ContactData> before = app.contact().list();
        ContactData contact = new ContactData()
                .withId(before.get(before.size() - 1).getId())
                .withFirstName("Anastasia").withLastName("Kutnenko")
                .withAddress("Shevchenka 100").withHomePhone("111-111-111")
                .withMobilePhone("0923456789").withWorkPhone("222-222-222")
                .withFax("333-333-333").withEmail1("anastasya.kutnenko+1@gmail.com")
                .withEmail2("anastasya.kutnenko+2@gmail.com")
                .withEmail3("anastasya.kutnenko+3@gmail.com");
        int index = before.size() - 1;
        app.contact().modify(contact, index);
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size());
        before.remove(index);
        before.add(contact);
        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(after, before);
    }
}
