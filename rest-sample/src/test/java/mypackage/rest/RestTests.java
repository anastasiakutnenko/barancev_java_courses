package mypackage.rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

public class RestTests extends TestBase{
    @Test
    public void testCreateIssue() throws IOException {
        skipIfNotFixed(1157);
        Set<Issue> oldIssues = getIssues();
        Issue newIssue = new Issue().withSubject("Test issue").withDescription("New Test issue");
        int issueId = createIssue(newIssue);
        Set<Issue> newIssues = getIssues();
        oldIssues.add(newIssue.withId(issueId));
        Assert.assertEquals(newIssues,oldIssues);
    }

    private Set<Issue> getIssues() throws IOException {
        String json = getExecutor()
                .execute(Request.Get("http://bugify.stqa.ru/api/issues.json"))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
    }

    private Executor getExecutor() {
        return Executor.newInstance().
                auth("288f44776e7bec4bf44fdfeb1e646490", "");
    }

    private int createIssue(Issue newIssue) throws IOException {
        String json = getExecutor()
                .execute(Request.Post("http://bugify.stqa.ru/api/issues.json")
                .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                new BasicNameValuePair("description", newIssue.getDescription())))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }
}
