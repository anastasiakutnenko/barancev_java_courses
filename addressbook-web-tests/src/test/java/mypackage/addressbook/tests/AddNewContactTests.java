package mypackage.addressbook.tests;

import mypackage.addressbook.model.ContactData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

public class AddNewContactTests extends TestBase {

    @Test
    public void addNewContactTest() throws Exception {
        List<ContactData> before = app.contact().list();
        ContactData contact = new ContactData()
                .withFirstName("Anastasia").withLastName("Kutnenko")
                .withAddress("Shevchenka 100").withHomePhone("111-111-111")
                .withMobilePhone("0923456789").withWorkPhone("222-222-222")
                .withFax("333-333-333").withEmail1("anastasya.kutnenko+1@gmail.com")
                .withEmail2("anastasya.kutnenko+2@gmail.com")
                .withEmail3("anastasya.kutnenko+3@gmail.com");
        app.goTo().contactCreationPage();
        app.contact().create(contact);
        app.goTo().homePage();
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size() + 1);
        contact.withId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
        before.add(contact);
        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(after, before);
    }
}
