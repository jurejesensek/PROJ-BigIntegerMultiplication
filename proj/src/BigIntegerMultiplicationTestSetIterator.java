import java.math.BigInteger;

import si.fri.algotest.entities.EVariable;
import si.fri.algotest.entities.VariableType;
import si.fri.algotest.entities.EResult;
import si.fri.algotest.entities.TestCase;
import si.fri.algotest.execute.DefaultTestSetIterator;
import si.fri.algotest.global.ErrorStatus;

public class BigIntegerMultiplicationTestSetIterator extends DefaultTestSetIterator {

    @Override
    public TestCase getCurrent() {
        if (currentInputLine == null) {
            ErrorStatus.setLastErrorMessage(ErrorStatus.ERROR, "The input is not valid!");
            return null;
        }

        // sort-project specific: line contains at least 4 fileds: testName, n1, n2 and result
        String[] fields = currentInputLine.split(":");
        if (fields.length < 4) {
            reportInvalidDataFormat("to few fields");
            return null;
        }

        String testName = fields[0];
        String group = fields[1];
        String factor1 = fields[2];
        String factor2 = fields[3];
        String result = fields[4];
        int numOfDigits = result.length();

        // do not delete the following lines; test-id parameter is compulsory

        EVariable testIDVar = EResult.getTestIDParameter("Test-" + Integer.toString(lineNumber));
        EVariable parameter1 = new EVariable("Test", "Test name", VariableType.STRING, testName);
        EVariable parameter0 = new EVariable("N", "Number of digits in result number", VariableType.INT, numOfDigits);
        EVariable parameter2 = new EVariable("Factor1", "First factor", VariableType.STRING, factor1);
        EVariable parameter3 = new EVariable("Factor2", "Second factor", VariableType.STRING, factor2);
        EVariable parameter4 = new EVariable("Result", "Result of the multiplication", VariableType.STRING, result);
        EVariable parameter5 = new EVariable("Group", "A name of a group of tests", VariableType.STRING, group);

        BigIntegerMultiplicationTestCase tCase = new BigIntegerMultiplicationTestCase();
        //ID
        tCase.addParameter(testIDVar);
        //input parameters
        tCase.addParameter(parameter0);
        //tCase.addParameter(parameter2);
        //tCase.addParameter(parameter3);
        //tCase.addParameter(parameter4);
        //tCase.addParameter(parameter5);

        int i = 0;
        switch (group) {
        case "INLINE":
            if (fields.length < 4) {
                reportInvalidDataFormat("to few fields");
                return null;
            }

            try {
                int a = 0;
                for (i = 2; i < 5; i++) {
                    for (int j = 0; j < fields[i].length(); j++) {
                        a = Integer.parseInt(fields[i].substring(j));
                    }
                }
            } catch (Exception e) {
                reportInvalidDataFormat("invalid type of inline data - data " + (i + 1));
                return null;
            }
            break;

        case "FILE":
            break;
        }

        tCase.firstFactor = new BigInteger(new BigInteger(factor1).toString(16), 16).toByteArray();
        tCase.secondFactor = new BigInteger(new BigInteger(factor2).toString(16), 16).toByteArray();
        tCase.result = new BigInteger(new BigInteger(result).toString(16), 16).toByteArray();

        return tCase;
    }
}
 