package fr.fpage.taskmaster.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class Configuration {

    private List<ProcessConfiguration> processConfiguration;

}
