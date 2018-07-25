package pro.biocontainers.readers.dockerhub.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import pro.biocontainers.readers.IRegistryContainer;
import pro.biocontainers.readers.Tuple;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Log4j
public class DockerHubContainer implements IRegistryContainer {

    private DockerHubContainerBriefInfo info;
    private List<DockerHubTag> tags;

    @Override
    public String getNameSpace() {
        return info.getNamespace();
    }

    @Override
    public String getName() {
        return info.getName();
    }

    @Override
    public Boolean isPrivate() {
        return info.getIs_private();
    }

    @Override
    public Integer getPullCount() {
        return info.getPull_count().intValue();
    }

    @Override
    public Date getLastUpdated() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            if(info.getLast_updated() != null){
                log.info(info.getLast_updated());
                date = formatter.parse(info.getLast_updated());
                return date;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean isStarred() {
        return info.getHas_starred();
    }

    @Override
    public Integer getStartCount() {
        return info.getStar_count().intValue();
    }

    @Override
    public String getDescription() {
        return info.getDescription();
    }

    @Override
    public List<Tuple<String, Integer>> getContainerTags() {
        return tags.stream().map(x -> new Tuple<>(x.getName(), x.getFull_size().intValue())).collect(Collectors.toList());
    }

    @Override
    public List<Tuple<Date, Integer>> getContainerStats() {
        return new ArrayList<>();
    }
}
