package nure.priadko.maksym;

public class ImportanceItem{
    private String title;
    private Integer imageResId;

    public ImportanceItem(String title, Integer imageResId){
        this.title = title;
        this.imageResId = imageResId;
    }

    public String GetTitle(){
        return title;
    }

    public Integer GetImageResId(){
        return imageResId;
    }
}