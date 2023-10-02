package balls.relics;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.cards.optionCards.FameAndFortune;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import balls.BallsInitializer;
import basemod.BaseMod;
import basemod.interfaces.StartActSubscriber;

public class DragonBall extends AbstractBallRelic implements ClickableRelic, StartActSubscriber {

    private static final String NAME = DragonBall.class.getSimpleName();
    public static final String RELIC_ID = BallsInitializer.makeID(NAME);

    private boolean used = false;

    public DragonBall() {
        super(RELIC_ID, NAME, RelicTier.UNCOMMON, LandingSound.FLAT);
        BaseMod.subscribe(this);
    }

    @Override
    public void atBattleStart() {
        if (!this.used) {
            this.beginLongPulse();
        }
    }

    @Override
    public void onVictory() {
        this.stopPulse();
    }

    @Override
    public void onRightClick() {
        if (!AbstractDungeon.getCurrRoom().isBattleOver && !this.used) {
            this.flash();
            this.stopPulse();
            this.grayscale = true;
            this.used = true;
            ArrayList<AbstractCard> wishes = new ArrayList<>();
            wishes.add(new BecomeAlmighty());
            wishes.add(new FameAndFortune());
            wishes.add(new LiveForever());
            for (AbstractCard c : wishes)
                c.upgrade();
            addToBot(new ChooseOneAction(wishes));
        }
    }

    @Override
    public void receiveStartAct() {
        this.used = false;
        this.grayscale = false;
    }
}
