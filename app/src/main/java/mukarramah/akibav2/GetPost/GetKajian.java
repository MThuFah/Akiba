package mukarramah.akibav2.GetPost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetKajian {

    @SerializedName("result")
    @Expose
    private List<Kajian> result = null;

    public List<Kajian> getResult() {
        return result;
    }

    public void setResult(List<Kajian> result) {
        this.result = result;
    }
}
