package com.rsacrypt;

import java.util.Random;

public class RSA {

    private String startNumber;
    private MyBigInteger publicKey;
    private MyBigInteger privateKey;
    private MyBigInteger modulus;

    public RSA() {

        this(new MyBigInteger("1"));
    }

    public RSA(MyBigInteger startNumber) {

        System.out.println("\nInitializing RSA (generating keys) ...");

        this.startNumber = startNumber.toString();
        MyBigInteger first = this.generatePrime(100);

        this.startNumber = first.toString();
        MyBigInteger second = this.generatePrime(100);

        this.modulus = first.multiply(second);
        MyBigInteger phi_modulus = first.subtract(MyBigInteger.ONE).multiply(second.subtract(MyBigInteger.ONE));

        MyBigInteger publicKey = new MyBigInteger("65537");
        do {
            publicKey = publicKey.add(MyBigInteger.TWO);
        } while (publicKey.isCoPrime(phi_modulus) == false);

        this.publicKey = publicKey;
        this.privateKey = this.publicKey.inverse_modulus(phi_modulus);

        System.out.println("\nPublic Key (e) = " + this.publicKey.toString());
        System.out.println("Private Key (d) = " + this.privateKey.toString());
        System.out.println("Modulus (n) = " + this.modulus.toString() + "\n");
    }

    public MyBigInteger generatePrime(int n) {

        MyBigInteger prime = new MyBigInteger(this.startNumber);
        Random random_number = new Random();
        int i = 0, count = random_number.nextInt(n) + 1;

        do {
            prime = prime.add(MyBigInteger.TWO);
            if (prime.isPrime()) {
                i++;
            }
        } while(i < count);

        return prime;
    }

    public MyBigInteger generatePrime() {

        return this.generatePrime(1);
    }

    public String encrypt(String plainText) {

        int length = plainText.length(), i;
        String plainTextNumber = "";

        for (i = 0; i < length; i++) {
            plainTextNumber += String.format("%03d", (int)plainText.charAt(i));
        }

        MyBigInteger plainTextInteger = new MyBigInteger(plainTextNumber);
        MyBigInteger cipherTextNumber = plainTextInteger.exponent_modulus(this.publicKey, this.modulus);

        return cipherTextNumber.toString();
    }

    public String decrypt(String cipherText) {

        MyBigInteger cipherTextInteger = new MyBigInteger(cipherText);
        MyBigInteger plainTextInteger = cipherTextInteger.exponent_modulus(this.privateKey, this.modulus);

        String plainTextNumber = plainTextInteger.toString();
        String text, plainText = "";
        int length = plainTextNumber.length(), i;

        for (i = length; i > 0; i -= 3) {
            if (i - 3 >= 0) {
                text = plainTextNumber.substring(i - 3, i);
            } else {
                text = plainTextNumber.substring(0, i);
            }
            plainText = String.valueOf(Character.toChars(Integer.parseInt(text))) + plainText;
        }

        return plainText;
    }

    public static void main(String[] args) {

        // http://doctrina.org/How-RSA-Works-With-Examples.html#
        MyBigInteger p = new MyBigInteger("12131072439211271897323671531612440428472427633701410925634549312301964373042085619324197365322416866541017057361365214171711713797974299334871062829803541");
        MyBigInteger q = new MyBigInteger("12027524255478748885956220793734512128733387803682075433653899983955179850988797899869146900809131611153346817050832096022160146366346391812470987105415233");
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