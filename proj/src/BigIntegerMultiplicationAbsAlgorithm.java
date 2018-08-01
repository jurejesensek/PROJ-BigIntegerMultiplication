import si.fri.algotest.entities.VariableSet;
import si.fri.algotest.entities.EVariable;
import si.fri.algotest.entities.VariableType;
import si.fri.algotest.entities.TestCase;
import si.fri.algotest.execute.AbsAlgorithm;
import si.fri.algotest.global.ErrorStatus;
import java.util.Arrays;

public abstract class BigIntegerMultiplicationAbsAlgorithm extends AbsAlgorithm {

  BigIntegerMultiplicationTestCase bigIntMultiplicationTestCase;
  
  byte[] result;

  @Override
  public TestCase getTestCase() {
    return bigIntMultiplicationTestCase;
  }

  @Override
  public ErrorStatus init(TestCase test) {
      if (test instanceof BigIntegerMultiplicationTestCase) {
          bigIntMultiplicationTestCase = (BigIntegerMultiplicationTestCase) test;
          int g = bigIntMultiplicationTestCase.result.length;
          result = new byte[g];
          return ErrorStatus.STATUS_OK;
      } else
          return ErrorStatus.setLastErrorMessage(ErrorStatus.ERROR_CANT_PERFORM_TEST, "Invalid test:" + test);
  }
  
  @Override
  public void run() {
      execute(bigIntMultiplicationTestCase.firstFactor, bigIntMultiplicationTestCase.secondFactor, result);
  }

  @Override
  public VariableSet done() {
    VariableSet variables = new VariableSet(bigIntMultiplicationTestCase.getParameters());

    boolean checkOK = byteArrayEquals(bigIntMultiplicationTestCase.result, result);
    EVariable passPar = new EVariable("Check", "", VariableType.STRING, checkOK ? "OK" : "NOK");
    variables.addVariable(passPar, true);
    
    return variables;
  }   

  private boolean byteArrayEquals(byte[] first, byte[] second) {
      if (Arrays.equals(first, second)) {
          return true;
      }
      return false;
  }

  protected abstract void execute(byte[] firstFactor, byte[] secondFactor, byte[] result);
}
