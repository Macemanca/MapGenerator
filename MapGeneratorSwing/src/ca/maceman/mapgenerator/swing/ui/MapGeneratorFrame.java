package ca.maceman.mapgenerator.swing.ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;

import ca.maceman.mapgenerator.swing.controller.MapGeneratorFrameController;

public class MapGeneratorFrame extends JFrame {

	private static MapGeneratorFrameController mapGeneratorFrameController;
	private JTextField txtWidth;
	private JTextField txtHeight;
	private JTextField txtBorderWidth;
	private JTextField txtSeed;
	private JLabel labelImageHolder;
	private JScrollPane mainScrollPane;
	private JPanel panelImg;
	private JPanel panelImgControls;

	/**
	 * Initialize the contents of the frame.
	 */
	public MapGeneratorFrame() {
		mapGeneratorFrameController = new MapGeneratorFrameController(this);

		panelImg = new JPanel();
		panelImg.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelImg.setBounds(334, 11, 1000, 1000);

		mainScrollPane = new JScrollPane(panelImg);
		mainScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		mainScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panelImg.setLayout(null);

		labelImageHolder = new JLabel("");
		panelImg.add(labelImageHolder);
		labelImageHolder.setBounds(0, 0, 421, 421);
		mainScrollPane.setBounds(334, 11, 655, 655);
		mainScrollPane.setViewportView(panelImg);
		this.getContentPane().add(mainScrollPane);

		JPanel pnlControls = new JPanel();
		pnlControls
				.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlControls.setBounds(10, 11, 314, 281);
		this.getContentPane().add(pnlControls);
		pnlControls.setLayout(null);

		JLabel lblWidth = new JLabel("Width:");
		lblWidth.setBounds(10, 11, 85, 14);
		pnlControls.add(lblWidth);

		txtWidth = new JTextField();
		txtWidth.setBounds(87, 8, 86, 20);
		pnlControls.add(txtWidth);
		txtWidth.setColumns(10);

		txtHeight = new JTextField();
		txtHeight.setBounds(87, 39, 86, 20);
		pnlControls.add(txtHeight);
		txtHeight.setColumns(10);

		JLabel lblHeight = new JLabel("Height:");
		lblHeight.setBounds(10, 42, 85, 14);
		pnlControls.add(lblHeight);

		final JSlider sliderAmp = new JSlider();
		sliderAmp.setPaintTicks(true);
		sliderAmp.setPaintLabels(true);
		sliderAmp.setMinorTickSpacing(1);
		sliderAmp.setValue(7);
		sliderAmp.setMinimum(5);
		sliderAmp.setMaximum(10);
		sliderAmp.setBounds(76, 70, 228, 23);
		pnlControls.add(sliderAmp);

		JLabel lblDetail = new JLabel("Amplitude:");
		lblDetail.setBounds(10, 67, 85, 14);
		pnlControls.add(lblDetail);

		JLabel lblBorderWidth = new JLabel("Border Width:");
		lblBorderWidth.setBounds(10, 107, 67, 14);
		pnlControls.add(lblBorderWidth);

		txtBorderWidth = new JTextField();
		txtBorderWidth.setBounds(87, 104, 86, 20);
		pnlControls.add(txtBorderWidth);
		txtBorderWidth.setColumns(10);

		JButton btnGenerateTerrain = new JButton("Generate Image");
		btnGenerateTerrain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mapGeneratorFrameController.GenerateNewMap(txtWidth.getText(),
						txtHeight.getText(), txtBorderWidth.getText(),
						sliderAmp.getValue(), txtSeed.getText());
				mapGeneratorFrameController.MapToPanel();
				panelImgControls.setVisible(true);

			}
		});

		btnGenerateTerrain.setBounds(10, 247, 294, 23);
		pnlControls.add(btnGenerateTerrain);

		JLabel lblRandomSeed = new JLabel("Random Seed:");
		lblRandomSeed.setBounds(10, 135, 76, 14);
		pnlControls.add(lblRandomSeed);

		JLabel lblPx = new JLabel("px");
		lblPx.setBounds(183, 14, 46, 14);
		pnlControls.add(lblPx);

		JLabel label = new JLabel("px");
		label.setBounds(183, 45, 46, 14);
		pnlControls.add(label);

		txtSeed = new JTextField();
		txtSeed.setBounds(87, 132, 199, 20);
		pnlControls.add(txtSeed);
		txtSeed.setColumns(10);

		panelImgControls = new JPanel();
		panelImgControls.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null,
				null));
		panelImgControls.setBounds(10, 303, 314, 191);
		this.getContentPane().add(panelImgControls);
		panelImgControls.setLayout(null);

		JButton btnSaveAsImage = new JButton("Save Image as .png");
		btnSaveAsImage.setBounds(10, 123, 294, 23);
		panelImgControls.add(btnSaveAsImage);

		JButton btnSaveAsTxt = new JButton("Dump Array to .txt");
		btnSaveAsTxt.setBounds(10, 157, 294, 23);
		panelImgControls.add(btnSaveAsTxt);

		JLabel lblScale = new JLabel("Scale:");
		lblScale.setBounds(10, 15, 54, 14);
		panelImgControls.add(lblScale);

		JButton btnScale2 = new JButton("X 2");
		btnScale2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnScale2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mapGeneratorFrameController.scaledImage(2);
			}
		});
		btnScale2.setBounds(74, 11, 70, 23);
		panelImgControls.add(btnScale2);

		JButton btnScale4 = new JButton("X 4");
		btnScale4.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnScale4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mapGeneratorFrameController.scaledImage(4);
			}
		});
		btnScale4.setBounds(154, 11, 70, 23);
		panelImgControls.add(btnScale4);

		JButton btnScale8 = new JButton("X 8");
		btnScale8.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnScale8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mapGeneratorFrameController.scaledImage(8);
			}
		});
		btnScale8.setBounds(234, 11, 70, 23);
		panelImgControls.add(btnScale8);

		JButton btnOriginal = new JButton("Original");
		btnOriginal.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnOriginal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mapGeneratorFrameController.MapToPanel();
			}
		});
		btnOriginal.setBounds(154, 45, 70, 23);
		panelImgControls.add(btnOriginal);

		JButton btnScale16 = new JButton("X 16");
		btnScale16.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnScale16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mapGeneratorFrameController.scaledImage(16);
			}
		});
		btnScale16.setBounds(74, 45, 70, 23);
		panelImgControls.add(btnScale16);
		panelImgControls.setVisible(false);

		this.setResizable(false);
		this.setTitle("Terrain Generator");
		this.setBounds(100, 100, 1015, 715);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
	}

}
