package validators;

public class SalaryValidator {

    private static final float SALARIO_MINIMO = 5;
    private static final float SALARIO_MAXIMO = 150;

    public static final String SALARY_ERROR_TYPE = "Error";
    public static final String SALARY_VALIDATOR_ERROR =
            String.format("El salario debe estar comprendida entre %s y %s", SALARIO_MAXIMO, SALARIO_MINIMO);

    public static boolean isValidSalary(final float salary) {

        return salary < SALARIO_MINIMO || salary > SALARIO_MAXIMO;
    }
}
