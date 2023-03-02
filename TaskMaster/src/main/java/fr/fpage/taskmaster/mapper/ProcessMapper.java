package fr.fpage.taskmaster.mapper;

import fr.fpage.backend.openapi.model.*;
import fr.fpage.backend.openapi.model.Process;
import fr.fpage.taskmaster.domain.GroupProcess;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ProcessMapper {
    ProcessMapper INSTANCE = Mappers.getMapper(ProcessMapper.class);

    default Process domainToApi(GroupProcess domain) {
        List<EnvValue> env = new ArrayList<>();
        domain.getConfiguration().getEnv().forEach((s, s2) -> env.add(new EnvValue().key(s).value(s2)));
        return new Process().name(domain.getConfiguration().getName())
                .nbInstance(domain.getConfiguration().getNbInstance())
                .startAtLaunch(domain.getConfiguration().isStartAtLaunch())
                .restartType(this.restartTypeDomainToApi(domain.getConfiguration().getRestartType()))
                .expectedExitCode(domain.getConfiguration().getExpectedExitCode())
                .startupTime(domain.getConfiguration().getStartupTime())
                .restartRetryCount(domain.getConfiguration().getRestartRetryCount())
                .exitSignal(this.exitSignalDomainToApi(domain.getConfiguration().getExitSignal()))
                .gracefulStopTime(domain.getConfiguration().getGracefulStopTime())
                .environement(env)
                .umask(domain.getConfiguration().getUmask());
    }

    RestartType restartTypeDomainToApi(fr.fpage.taskmaster.model.RestartType domain);
    ExitSignalType exitSignalDomainToApi(fr.fpage.taskmaster.model.ExitSignalType domain);

}
