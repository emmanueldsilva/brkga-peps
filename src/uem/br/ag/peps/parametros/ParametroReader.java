package uem.br.ag.peps.parametros;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ParametroReader {
	
	private List<ParametroLinha> parametros = new ArrayList<ParametroLinha>();

	public ParametroReader(String path) throws IOException {
	    final Stream<String> lines = Files.lines(Paths.get(path));
	    lines.forEach(readParametro());
	    lines.close();
	}

	private Consumer<String> readParametro() {
		return new Consumer<String>() {

			@Override
			public void accept(String t) {
				ParametroLinha parametroLinha = null;
				if (t.matches(ParametroEmployeeNumber.pattern())) {
					parametroLinha = new ParametroEmployeeNumber(t);
				} else if (t.matches(ParametroEmployeeSalary.pattern())) {
					parametroLinha = new ParametroEmployeeSalary(t);
				} else if (t.matches(ParametroEmployeeSkill.pattern())) {
					parametroLinha = new ParametroEmployeeSkill(t);
				} else if (t.matches(ParametroEmployeeSkillNumber.pattern())) {
					parametroLinha = new ParametroEmployeeSkillNumber(t);
				} else if (t.matches(ParametroGraphArc.pattern())) {
					parametroLinha = new ParametroGraphArc(t);
				} else if (t.matches(ParametroGraphArcNumber.pattern())) {
					parametroLinha = new ParametroGraphArcNumber(t);
				} else if (t.matches(ParametroSkillNumber.pattern())) {
					parametroLinha = new ParametroSkillNumber(t);
				} else if (t.matches(ParametroTaskCost.pattern())) {
					parametroLinha = new ParametroTaskCost(t);
				} else if (t.matches(ParametroTaskNumber.pattern())) {
					parametroLinha = new ParametroTaskNumber(t);
				} else if (t.matches(ParametroTaskSkill.pattern())) {
					parametroLinha = new ParametroTaskSkill(t);
				} else if (t.matches(ParametroTaskSkillNumber.pattern())) {
					parametroLinha = new ParametroTaskSkillNumber(t);
				}
				
				parametros.add(parametroLinha);
			}
		};
	}

	public static void main(String[] args) throws IOException {
		new ParametroReader("/home/emmanuel/projetos/ag-peps/resources/problem-generator/inst10-5-10-5.conf");
	}
}
