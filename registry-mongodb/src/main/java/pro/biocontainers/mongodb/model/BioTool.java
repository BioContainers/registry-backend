package pro.biocontainers.mongodb.model;

import lombok.Builder;
import lombok.Data;
import pro.biocontainers.data.model.Tuple;

import java.util.List;
import java.util.Objects;

@Data
@Builder
public class BioTool {

    String id;
    String name;
    List<String> topics;
    String description;
    String homePage;
    List<DownloadURL> downloadURLS;
    String license;
    List<Tuple<String, String>> contacts;
    List<BioToolPublication> publications;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BioTool bioTool = (BioTool) o;
        return Objects.equals(id, bioTool.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
