package com.rsacrypt;

import java.util.Random;

public class RSA {

    private String startNumber = "1";

    public RSA() {
    }

    public MyBigInteger generatePrime(int n) {

        MyBigInteger prime = new MyBigInteger(this.startNumber);
        Random random_number = new Random();
        int i = 0, increment;

        do {

            do {
                increment = random_number.nextInt(100);
            } while (increment % 2 == 1);

            prime = prime.add(new MyBigInteger(String.valueOf(increment)));
            if (prime.isPrime()) {
                i++;
            }

        } while(i <= n);

        return prime;
    }

    public MyBigInteger generatePrime() {

        return this.generatePrime(1);
    }

    public String encrypt(String plainText) {

        return "";
    }

    public String decrypt(String cipherText) {

        return "";
    }

    public static void main(String[] args) {
	    // write your code here

        // http://doctrina.org/How-RSA-Works-With-Examples.html#

        RSA algo = new RSA();
        MyBigInteger p = new MyBigInteger("12131072439211271897323671531612440428472427633701410925634549312301964373042085619324197365322416866541017057361365214171711713797974299334871062829803541");//algo.generatePrime(5);
        algo.startNumber = p.toString();
        MyBigInteger q = new MyBigInteger("12027524255478748885956220793734512128733387803682075433653899983955179850988797899869146900809131611153346817050832096022160146366346391812470987105415233");//algo.generatePrime(10);
        MyBigInteger n = p.multiply(q);

        MyBigInteger p1 = p.subtract(MyBigInteger.ONE);
        MyBigInteger q1 = q.subtract(MyBigInteger.ONE);
        MyBigInteger phiN = p1.multiply(q1);
        MyBigInteger e = new MyBigInteger("65537");
        MyBigInteger d = e.inverse_modulus(phiN);

        String matchN = "145906768007583323230186939349070635292401872375357164399581871019873438799005358938369571402670149802121818086292467422828157022922076746906543401224889672472407926969987100581290103199317858753663710862357656510507883714297115637342788911463535102712032765166518411726859837988672111837205085526346618740053";
        String matchPhiN = "145906768007583323230186939349070635292401872375357164399581871019873438799005358938369571402670149802121818086292467422828157022922076746906543401224889648313811232279966317301397777852365301547848273478871297222058587457152891606459269718119268971163555070802643999529549644116811947516513938184296683521280";
        String matchD = "89489425009274444368228545921773093919669586065884257445497854456487674839629818390934941973262879616797970608917283679875499331574161113854088813275488110588247193077582527278437906504015680623423550067240042466665654232383502922215493623289472138866445818789127946123407807725702626644091036502372545139713";

        System.out.println("\np  = " + p);
        System.out.println("p1 = " + p1);

        System.out.println("\nq  = " + q);
        System.out.println("q1 = " + q1);
        System.out.println("\nn = " + n);
        System.out.println("n = " + matchN);
        System.out.println("Match N = " + (n.toString().compareTo(matchN) == 0 ? "true" : "false"));
        System.out.println("\nphiN = " + phiN);
        System.out.println("phiN = " + matchPhiN);
        System.out.println("Match PhiN = " + (phiN.toString().compareTo(matchPhiN) == 0 ? "true" : "false"));

        System.out.println("\ngcd(e, phiN) == 1 (mod phiN) = " + phiN.isCoPrime(e));
        System.out.println("e = " + e);

        System.out.println("\nd = " + d);
        System.out.println("d = " + matchD);
        System.out.println("Match d = " + (d.toString().compareTo(matchD) == 0 ? "true" : "false"));
    }
}
