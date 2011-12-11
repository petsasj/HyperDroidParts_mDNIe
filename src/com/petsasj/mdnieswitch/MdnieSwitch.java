package com.petsasj.mdnieswitch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.stericson.RootTools.RootTools;

public class MdnieSwitch extends Activity implements RadioGroup.OnCheckedChangeListener {

	private RadioGroup profileGroup;
    private RadioGroup configGroup;
    private RadioButton[][] rb;
    String[][] profiles = {
		{"Default", "Video", "Warm", "Cold", "Camera", "Navi", "Gallery"}, 
		{"Dynamic", "Standard", "Movie", "Petsasj", "Doctorcete"}
	};
    

    
	//@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        RootTools.isAccessGiven();
                
        profileGroup=(RadioGroup)findViewById(R.id.profileGroup);
        configGroup=(RadioGroup)findViewById(R.id.configGroup);
        profileGroup.setOnCheckedChangeListener(this);         
        configGroup.setOnCheckedChangeListener(this);
        
        rb= new RadioButton[2][7];
        rb[0][0] = (RadioButton) findViewById(R.id.profile0);
        rb[0][1] = (RadioButton) findViewById(R.id.profile1);
        rb[0][2] = (RadioButton) findViewById(R.id.profile2);
        rb[0][3] = (RadioButton) findViewById(R.id.profile3);
        rb[0][4] = (RadioButton) findViewById(R.id.profile4);
        rb[0][5] = (RadioButton) findViewById(R.id.profile5);
        rb[0][6] = (RadioButton) findViewById(R.id.profile6);

        rb[1][0] = (RadioButton) findViewById(R.id.config0);
        rb[1][1] = (RadioButton) findViewById(R.id.config1);
        rb[1][2] = (RadioButton) findViewById(R.id.config2);
        rb[1][3] = (RadioButton) findViewById(R.id.config3);
        rb[1][4] = (RadioButton) findViewById(R.id.config4);

}

		public void onCheckedChanged (RadioGroup group, int checkedId) {
		int typ=0;
                // declare default for profile
		String cmd="echo %s > /sys/devices/virtual/mdnieset_ui/switch_mdnieset_ui/mdnieset_ui_file_cmd";
                // overwrite command id config 

		if(group==configGroup) {
			cmd = "echo %s > /sys/devices/virtual/mdnieset_ui/switch_mdnieset_ui/mdnieset_user_select_file_cmd";
			typ=1;
		}

                //  cache check id
                String check=null;
                
                // durchlaufe alle deklarierten buttons und suche den markierten
				for(RadioButton r : rb[typ]){
					if(r.isChecked()) {
						check = r.getText().toString();
						break;
					}
				}
                Log.i("** Ramnitz Inc.**", "Found: "+check);
				// wenn keiner aktiv abbrechen 
				if (check==null) return;
				int active=0;
				for(active=0; active<profiles[typ].length; active++){
					
					if(profiles[typ][active].equalsIgnoreCase(check))  break;
				};
				Log.i("** Ramnitz Inc.**", "Detected: "+active);
				try {
                	cmd = String.format(cmd, active);
                	Log.i("** Ramnitz Inc.**", cmd);
                    RootTools.sendShell(cmd);
                    
                } catch (Exception e) {
                    Log.d("*** DEBUG ***", "Unexpected error: "+e.getMessage());                    
                } 
                
			}
		}
