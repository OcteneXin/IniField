package com.octenexin.inifield.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * this class will load string arr(non-local) to a map when game starts.
 * */
public class DialogLoader {

    /**
     * key: dialog name, like register name like "inifield_talk_nc_first_pacific"
     *      a key means an integrated dialog process
     * value: a batch of string
     *      dialog has progress, we use suffix to mark stage. Player rarely say something, so all character dialog.
     *          inifield.talk.nc.first.pacific.stage_0
     *      dialog may fall into options, so the batch contains more than one option, with different suffix, like
     *          inifield.talk.nc.first.pacific.stage_5.ver0
     *          inifield.talk.nc.first.pacific.stage_7.ver0.ver1
     *      dialog may have player choice
     *          inifield.talk.nc.first.pacific.pl_choice0
     *
     *
     * all these can't be optioned by some para. we leave click event(keyboard event) for any dialog to overwrite.
     * such string arr never transmit through paras, we call it directly in function
     *
     *
     * we make a rule for lang coding name:
     * |___inifield: mod name
     * |___type: dialog happens in what situation?
     *      |___talk: normal talk
     *      |___fight: during fight
     *      |___look: player's observation
     * |___person: the person you talk to.
     *      |___may have more than one, use _ to split
     * |___desc
     *      |___time
     *      |___position
     *          |___dimension
     *          |___biome
     *          |___structure
     *      |___route
     *          |___pacific
     *          |___neutral
     *          |___genocide
     *      |___stats
     *          |___killed what
     *          |___make friends
     *          |___achievement
     *          |___item in hand
     *          |___vec angle
     * |___stage: all person will count, but pl_choice won't
     * |___pl_choice: the num will increase simply. Don't appear with stage
     * |___before query: player's choice in last query. Don't appear with pl_choice
     *      |___ver0
     *      |___ver1
     * */
    public static final Map<String,String[]> ALL_DIALOG_MAP=new HashMap<>();



}
