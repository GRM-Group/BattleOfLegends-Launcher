package pl.grm.bol.launcher.boxes;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import pl.grm.bol.launcher.Presenter;
import pl.grm.bol.launcher.net.updater.UpdaterStarter;
import pl.grm.bol.lib.BLog;

public class LauncherUpdateDialog extends JDialog {
	private static final long	serialVersionUID	= 1L;
	private final JPanel		contentPanel		= new JPanel();
	private JProgressBar		progressBar;
	
	/**
	 * Create the dialog.
	 */
	public LauncherUpdateDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		Container content = getContentPane();
		progressBar = new JProgressBar();
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		Border border = BorderFactory.createTitledBorder("Updating Launcher ...");
		progressBar.setBorder(border);
		content.add(progressBar, BorderLayout.NORTH);
		setSize(300, 100);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void updateLauncher(Presenter presenter) {
		progressBar.setValue(7);
		BLog logger = presenter.getConfigHandler().getLogger();
		logger.info("There is new Launcher version!");
		logger.info("Starting update ...");
		progressBar.setStringPainted(true);
		progressBar.setToolTipText("Downloading updater");
		progressBar.setValue(9);
		if (UpdaterStarter.startUpdater(logger, progressBar)) {
			logger.info("Restarting launcher");
			progressBar.setToolTipText("Restarting launcher");
			progressBar.setString("Restarting launcher");
			progressBar.setValue(100);
			try {
				Thread.sleep(1500L);
			}
			catch (InterruptedException e) {
				logger.info("Launcher update failed!");
				logger.log(Level.SEVERE, e.toString(), e);
				JOptionPane.showMessageDialog(this, "Update of launcher encauntered an error!",
						"Update failed!", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			logger.info("Launcher update failed!");
			JOptionPane.showMessageDialog(this, "Update of launcher failed!", "Update failed!",
					JOptionPane.ERROR_MESSAGE);
		}
		dispose();
	}
}
