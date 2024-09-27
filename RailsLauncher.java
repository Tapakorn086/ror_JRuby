
// java -cp lib/jruby-complete-9.4.8.0.jar:. RailsLauncher for run
import org.jruby.Ruby;
import org.jruby.RubyInstanceConfig;
import org.jruby.embed.ScriptingContainer;

import java.io.File;

public class RailsLauncher {
    public static void main(String[] args) {
        // Set the absolute path to the Rails project directory
        String projectRoot = new File(".").getAbsolutePath();  // or manually set the project path

        // Configure JRuby runtime
        RubyInstanceConfig config = new RubyInstanceConfig();
        config.setLoader(RailsLauncher.class.getClassLoader());

        // Create a new JRuby runtime instance
        Ruby runtime = Ruby.newInstance(config);

        // Create a JRuby ScriptingContainer to run Ruby scripts
        ScriptingContainer container = new ScriptingContainer();
        container.setCurrentDirectory(projectRoot);  // Set working directory to Rails project root

        // Load the Rails environment by providing the absolute path to config/environment.rb
        container.runScriptlet("require '" + projectRoot + "/config/environment.rb'");

        // Start the Rails server using Puma
        container.runScriptlet("require 'rack'");
        container.runScriptlet("require 'rack/handler/puma'");
        container.runScriptlet("Rack::Handler::Puma.run Rails.application");
    }
}
