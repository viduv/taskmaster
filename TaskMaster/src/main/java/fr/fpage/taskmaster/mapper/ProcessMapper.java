package fr.fpage.taskmaster.mapper;

import fr.fpage.backend.openapi.model.EnvValue;
import fr.fpage.backend.openapi.model.ExitSignalType;
import fr.fpage.backend.openapi.model.RestartType;
import fr.fpage.taskmaster.domain.GroupProcess;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ProcessMapper {
    ProcessMapper INSTANCE = Mappers.getMapper(ProcessMapper.class);

    default fr.fpage.backend.openapi.model.GroupProcess domainToApi(GroupProcess domain) {
        List<EnvValue> env = new ArrayList<>();
        domain.getConfiguration().getEnv().forEach((s, s2) -> env.add(new EnvValue().key(s).value(s2)));
        return new fr.fpage.backend.openapi.model.GroupProcess().name(domain.getConfiguration().getName())
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

    default fr.fpage.backend.openapi.model.GroupProcessDetails domainToApiDetail(GroupProcess domain) {
        List<EnvValue> env = new ArrayList<>();
        domain.getConfiguration().getEnv().forEach((s, s2) -> env.add(new EnvValue().key(s).value(s2)));
        fr.fpage.backend.openapi.model.GroupProcess gp = this.domainToApi(domain);
        return new fr.fpage.backend.openapi.model.GroupProcessDetails().groupProcess(gp).processes(
                domain.getProcesses().stream().map(this::domainProcessToOpenApi).toList()
        );
    }

    RestartType restartTypeDomainToApi(fr.fpage.taskmaster.model.RestartType domain);
    ExitSignalType exitSignalDomainToApi(fr.fpage.taskmaster.model.ExitSignalType domain);

    @Mapping(target = "pid", expression = "java((int)domain.getProcess().pid())")
    fr.fpage.backend.openapi.model.Process domainProcessToOpenApi(fr.fpage.taskmaster.domain.Process domain);
}
