package walking_web.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Orderline {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long lineId;

    @ManyToOne
    @JoinColumn(name = "shoe")
    private Shoe shoe;

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public void setShoe(Shoe shoe) {
        this.shoe = shoe;
    }

}
