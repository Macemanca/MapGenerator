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

import ca.maceman.mapgenerator.commons.model.TileMap;
import ca.maceman.mapgenerator.swing.controller.MapGeneratorFrameController;
import javax.swing.JCheckBox;

/**
 * Windows for the map generator GUI
 * 
 * @author Andy
 *
 */
public class MapGeneratorFrame extends JFrame {
	private static final long serialVersionUID = -8679194374215117111L;
	private static MapGeneratorFrameController mapGeneratorFrameController;
	private JTextField txtWidth;
	private JTextField txtHeight;
	private JTextField txtSeed;
	private JLabel labelImageHolder;
	private JScrollPane mainScrollPane;
	private JPanel panelImg;
	private JPanel panelImgControls;
	private TileMap tileMap;
	private JCheckBox chckbxIslandMap;

	/**
	 * Initialize the contents of the frame.
	 */
	public MapGeneratorFrame() {
		mapGeneratorFrameController = new MapGeneratorFrameController(this);

		panelImg = new JPanel();
		panelImg.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelImg.setBounds(334, 11, 1000, 1000);

		mainScrollPane = new JScrollPane(panelImg);
		mainScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		mainScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panelImg.setLayout(null);

		labelImageHolder = new JLabel("");
		panelImg.add(labelImageHolder);
		labelImageHolder.setBounds(0, 0, 421, 421);
		mainScrollPane.setBounds(334, 11, 655, 655);
		mainScrollPane.setViewportView(panelImg);
		this.getContentPane().add(mainScrollPane);

		JPanel pnlControls = new JPanel();
		pnlControls.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
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
		sliderAmp.setMinimum(3);
		sliderAmp.setMaximum(10);
		sliderAmp.setBounds(76, 70, 228, 23);
		pnlControls.add(sliderAmp);

		JLabel lblDetail = new JLabel("Amplitude:");
		lblDetail.setBounds(10, 67, 85, 14);
		pnlControls.add(lblDetail);

		final JSlider sliderBlend = new JSlider();
		sliderBlend.setValue(7);
		sliderBlend.setPaintTicks(true);
		sliderBlend.setPaintLabels(true);
		sliderBlend.setPaintTrack(true);
		sliderBlend.setMinorTickSpacing(1);
		sliderBlend.setMinimum(0);
		sliderBlend.setMaximum(10);
		sliderBlend.setBounds(76, 100, 228, 23);
		pnlControls.add(sliderBlend);

		JLabel lblBlending = new JLabel("Blending:");
		lblBlending.setBounds(10, 97, 85, 14);
		pnlControls.add(lblBlending);

		JButton btnGenerateTerrain = new JButton("Generate Image");
		btnGenerateTerrain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tileMap = mapGeneratorFrameController.GenerateNewMap(txtWidth.getText(), txtHeight.getText(), sliderAmp.getValue(), sliderBlend.getValue(), chckbxIslandMap.isSelected(), txtSeed.getText());
				mapGeneratorFrameController.MapToPanel(tileMap);
				panelImgControls.setVisible(true);

			}
		});

		btnGenerateTerrain.setBounds(10, 247, 294, 23);
		pnlControls.add(btnGenerateTerrain);

		JLabel lblRandomSeed = new JLabel("Random Seed:");
		lblRandomSeed.setBounds(10, 134, 76, 14);
		pnlControls.add(lblRandomSeed);

		JLabel lblPx = new JLabel("px");
		lblPx.setBounds(183, 14, 46, 14);
		pnlControls.add(lblPx);

		JLabel label = new JLabel("px");
		label.setBounds(183, 45, 46, 14);
		pnlControls.add(label);

		txtSeed = new JTextField();
		txtSeed.setBounds(87, 131, 199, 20);
		pnlControls.add(txtSeed);
		txtSeed.setColumns(10);

		chckbxIslandMap = new JCheckBox("Island Map");
		chckbxIslandMap.setBounds(6, 166, 97, 23);
		pnlControls.add(chckbxIslandMap);

		panelImgControls = new JPanel();
		panelImgControls.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
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
				mapGeneratorFrameController.MapToPanel(tileMap);
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

	public static MapGeneratorFrameController getMapGeneratorFrameController() {
		return mapGeneratorFrameController;
	}

	public static void setMapGeneratorFrameController(MapGeneratorFrameController mapGeneratorFrameController) {
		MapGeneratorFrame.mapGeneratorFrameController = mapGeneratorFrameController;
	}

	public JTextField getTxtWidth() {
		return txtWidth;
	}

	public void setTxtWidth(JTextField txtWidth) {
		this.txtWidth = txtWidth;
	}

	public JTextField getTxtHeight() {
		return txtHeight;
	}

	public void setTxtHeight(JTextField txtHeight) {
		this.txtHeight = txtHeight;
	}

	public JTextField getTxtSeed() {
		return txtSeed;
	}

	public void setTxtSeed(JTextField txtSeed) {
		this.txtSeed = txtSeed;
	}

	public JLabel getLabelImageHolder() {
		return labelImageHolder;
	}

	public void setLabelImageHolder(JLabel labelImageHolder) {
		this.labelImageHolder = labelImageHolder;
	}

	public JScrollPane getMainScrollPane() {
		return mainScrollPane;
	}

	public void setMainScrollPane(JScrollPane mainScrollPane) {
		this.mainScrollPane = mainScrollPane;
	}

	public JPanel getPanelImg() {
		return panelImg;
	}

	public void setPanelImg(JPanel panelImg) {
		this.panelImg = panelImg;
	}

	public JPanel getPanelImgControls() {
		return panelImgControls;
	}

	public void setPanelImgControls(JPanel panelImgControls) {
		this.panelImgControls = panelImgControls;
	}

	public JCheckBox getChckbxIslandMap() {
		return chckbxIslandMap;
	}
}
