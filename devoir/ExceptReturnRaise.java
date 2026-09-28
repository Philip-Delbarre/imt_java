public class ExceptReturnRaise extends Exception {
    
    
    public ExceptReturnRaise(int err)
    {
        if (err < 0 || err == 0 ) 
        System.out.println ("opération de suppression impossible" );
    }

}
