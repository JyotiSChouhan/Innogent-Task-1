class Recur
{
	static int fact=1;//method area
	public static void main(String ar[])//method area main()
	{
		int n=15;//stack
		factorial(n);//call stack 
		System.out.println("factorial of : "+n+" using recursion is = "+fact);
	}
	static void factorial(int i)//stack
	{
		if(i<1) return;
		factorial(i-1);
		fact*=i;
	}
}
class Loop
{
	public static void main(String ar[])//stack main()
	{
		int fact=1;//stack
        int n=15;//stack
		for(int i=1;i<=n;i++)//stack
		{
			fact*=i;
		}
		System.out.println("factorial of : "+n+" using loop is = "+fact);
	}
}