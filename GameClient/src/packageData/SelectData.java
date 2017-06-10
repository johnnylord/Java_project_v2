package packageData;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

// SelectData
// selectedHero data

public class SelectData implements Serializable{ 

		// User Setting 
	public String heroName;

	public SelectData(String heroName){
		this.heroName = heroName;
	}
};
