import java.io.IOException;

public class ExpressionEvaluation{
    public static byte[][] Prior = {{'>', '>', '<', '<', '<', '>', '>', '<'}, {'>', '>', '<', '<', '<', '>', '>', '<'}, {'>', '>', '>', '>', '<', '>', '>', '<'}, {'>', '>', '>', '>', '<', '>', '>', '<'}, {'<', '<', '<', '<', '<', '=', ' ', '<'}, {'>', '>', '>', '>', ' ', '>', '>', '>'}, {'<', '<', '<', '<', '<', ' ', '=', '<'}, {'>', '>', '>', '>', '<', '>', '>', '>'}};

    public static int PushChar(byte[] s, byte c, int top)
    {
        s[top] = c;
        return top + 1;
    }

    public static int PushNum(float[] s, float x, int top)
    {
        s[top] = x;
        return top + 1;
    }

    public static int PopChar(byte[] s, int top)
    {
        return top - 1;
    }

    public static int PopNum(float[] s, int top)
    {
        return top - 1;
    }

    public static void strcat(byte[] str1, byte[] str2, byte[] result)
    {
        if (str1 == null && str2 == null)
        {
            result = null;
            return;
        }
        int i = 0;
        int j = 0;
        while (str1[i] != 10)
        {
            result[i] = str1[i];
            (i)++;
        }
        while (str2[j] != 10)
        {
            result[i] = str2[j];
            str1[(i)++] = str2[(j)++];
        }
        str1[i] = 10;
        result[i] = 10;
    }

    public static void strcpy(byte[] dest, byte[] source, byte[] result)
    {
        int i = 0;
        while (1)
        {
            dest[i] = source[i];
            result[i] = source[i];
            if (source[i] == 10)
                break;
            (i)++;
        }
    }

    public static float Operate(float a, byte theta, float b)
    {
        if (theta == '+')
        {
            return a + b;
        }
        else
            if (theta == '-')
            {
                return a - b;
            }
            else
                if (theta == '*')
                {
                    return a * b;
                }
                else
                    if (theta == '/')
                    {
                        return a / b;
                    }
                    else
                    {
                        return 0;
                    }
    }

    public static byte[] OPSET = {'+', '-', '*', '/', '(', ')', '#', '^'};

    public static int In(byte Test, byte[] TestOp)
    {
        int Find = 0;
        for (int i = 0; i < 8; (i)++)
        {
            if (Test == TestOp[i])
                Find = 1;
        }
        return Find;
    }

    public static int ReturnOpOrd(byte op, byte[] TestOp)
    {
        for (int i = 0; i < 8; (i)++)
        {
            if (op == TestOp[i])
                return i;
        }
    }

    public static byte precede(byte Aop, byte Bop)
    {
        return Prior[ReturnOpOrd(Aop, OPSET)][ReturnOpOrd(Bop, OPSET)];
    }

    public static float EvaluateExpression(byte[] MyExpression)
    {
        byte[] OPTR = new byte[100];
        int optr_top = 0;
        float[] OPND = new float[100];
        int opnd_top = 0;
        byte[] TempData = new byte[20];
        float Data;
        float a;
        float b;
        byte theta;
        byte[] c = new byte[100];
        byte[] TempC = new byte[100];
        byte[] Dr = {'#', 10};
        optr_top = PushChar(OPTR, '#', optr_top);
        strcat(MyExpression, Dr, c);
        strcpy(TempData, "\0", TempC);
        int temp = 0;
        while (c[temp] != '#' || OPTR[optr_top - 1] != '#')
        {
            if (!(In(c[temp], OPSET)))
            {
                Dr[0] = c[temp];
                strcat(TempData, Dr, TempC);
                (temp)++;
                if (In(c[temp], OPSET))
                {
                    Data = atof(TempData);
                    opnd_top = PushNum(OPND, Data, opnd_top);
                    strcpy(TempData, "\0", TempC);
                }
            }
            else
            {
                byte preTemp = precede(OPTR[optr_top - 1], c[temp]);
                if (preTemp == '<')
                {
                    optr_top = PushChar(OPTR, c[temp], optr_top);
                    (temp)++;
                }
                else
                    if (preTemp == '=')
                    {
                        optr_top = PopChar(OPTR, optr_top);
                        (temp)++;
                    }
                    else
                        if (preTemp == '>')
                        {
                            theta = OPTR[optr_top - 1];
                            optr_top = PopChar(OPTR, optr_top);
                            b = OPND[opnd_top - 1];
                            opnd_top = PopNum(OPND, opnd_top);
                            a = OPND[opnd_top - 1];
                            opnd_top = PopNum(OPND, opnd_top);
                            opnd_top = PushNum(OPND, Operate(a, theta, b), opnd_top);
                        }
            }
        }
        return OPND[opnd_top - 1];
    }

    public static void main(String[] args) throws IOException
    {
        byte[] s = "1+(3*4)/(2-1)\0";
        System.out.print(EvaluateExpression(s));
        return;
    }
}
