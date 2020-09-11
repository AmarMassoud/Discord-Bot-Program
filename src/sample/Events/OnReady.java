package sample.Events;

import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import sample.Main;

import javax.annotation.Nonnull;

public class OnReady extends ListenerAdapter {

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        Main main = new Main();
        main.setJda(event.getJDA());


    }

    @Override
    public void onDisconnect(@Nonnull DisconnectEvent event) {

    }

    @Override
    public void onShutdown(@Nonnull ShutdownEvent event) {

    }
}
