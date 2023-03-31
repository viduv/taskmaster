package fr.fpage.taskmaster.mapper;

import fr.fpage.backend.openapi.model.EnvValue;
import fr.fpage.backend.openapi.model.ExitSignalType;
import fr.fpage.backend.openapi.model.ProcessEtat;
import fr.fpage.backend.openapi.model.RestartType;
import fr.fpage.taskmaster.domain.GroupProcess;
import fr.fpage.taskmaster.domain.Process;
import fr.fpage.taskmaster.model.ProcessConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface ProcessMapper {
    ProcessMapper INSTANCE = Mappers.getMapper(ProcessMapper.class);

    default fr.fpage.backend.openapi.model.GroupProcess domainToApi(GroupProcess domain) {
        List<EnvValue> env = new ArrayList<>();
        domain.getConfiguration().getEnv().forEach((s, s2) -> env.add(new EnvValue().key(s).value(s2)));
        ProcessEtat etat = null;
        for (Process process : domain.getProcesses()) {
            if (etat == null) {
                etat = process.getEtat();
            } else if (!etat.equals(process.getEtat())) {
                etat = ProcessEtat.PARTIAL;
            }
        }
        return new fr.fpage.backend.openapi.model.GroupProcess().name(domain.getConfiguration().getName())
                .nbInstance(domain.getConfiguration().getNbInstance())
                .command(domain.getConfiguration().getCmd())
                .startAtLaunch(domain.getConfiguration().isStartAtLaunch())
                .restartType(this.restartTypeDomainToApi(domain.getConfiguration().getRestartType()))
                .expectedExitCode(domain.getConfiguration().getExpectedExitCode())
                .startupTime(domain.getConfiguration().getStartupTime())
                .restartRetryCount(domain.getConfiguration().getRestartRetryCount())
                .exitSignal(this.exitSignalDomainToApi(domain.getConfiguration().getExitSignal()))
                .gracefulStopTime(domain.getConfiguration().getGracefulStopTime())
                .environement(env)
                .etat(etat)
                .umask(domain.getConfiguration().getUmask())
                .stdout(domain.getConfiguration().getStdoutFile())
                .stderr(domain.getConfiguration().getStderrFile())
                .workingdir(domain.getConfiguration().getFolder());
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

    @Mapping(target = "pid", expression = "java(domain.getMainRunnable() == null ? 0:(int)domain.getMainRunnable().getJavaProcess().pid())")
    fr.fpage.backend.openapi.model.Process domainProcessToOpenApi(fr.fpage.taskmaster.domain.Process domain);

    @Mapping(target = "cmd", source = "command")
    @Mapping(target = "stdoutFile", source = "stdout")
    @Mapping(target = "stderrFile", source = "stderr")
    @Mapping(target = "folder", source = "workingdir")
    @Mapping(target = "env", source = "environement")
    ProcessConfiguration groupProcessApiToGroupProcessConfiguration(fr.fpage.backend.openapi.model.GroupProcess groupProcess);

    default HashMap<String, String> envValueToEnvMap(List<EnvValue> env) {
        HashMap<String, String> map = new HashMap<>();
        env.forEach(envValue -> map.put(envValue.getKey(), envValue.getValue()));
        return map;
    }
}
