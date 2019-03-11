/**
 *@author: Lenny Ardiles
 *@description: A class for a single playing card
 *@date: 05-05-2001
 *Last Modified: 05-05-2001
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import cards.*;

public class BlackJackFrame extends JFrame{
	JMenuBar jMenuBarBJ;
	TripToCasino trip;
	Dealer dealer;
	public JPanel jPanelData;
	public BlackJackTable jPanelDealer;// = new BlackJackTable();
	public BlackJackTable jPanelPlayer;// = new BlackJackTable();
	public JPanel jPanelControls = new JPanel();
	JTextField jTextFieldPlayer;
	JTextField jTextFieldCash;
	JTextField jTextFieldBet;
	JTextField jTextFieldGameWinnings;
//	JTextField jTextFieldTotalWinnings; 		//add Later
	JButton jButtonSplit;
	JButton jButtonDouble;
	JButton jButtonStay;
	JButton jButtonHit;
	JButton jButtonDeal;
	JMenuItem jMenuItemDeal;
	JCheckBoxMenuItem jMenuItemShowDeck;
	ResultImage image;
	JProgressBar jProgressBarDeck;
	private JLabel jLabelPlayer;
	private JLabel jLabelCash;
	private JLabel jLabelBet;
	private JLabel jLabelGameWinnings;
//	private JLabel jLabelTotalWinnings;			//add Later
	private String dealerName;
	private String playerName;

///TestingCardImage
///	CardImage c1 = new CardImage( new String("S"), new String("A")  );
///	CardImage c2 = new CardImage( new String("D"), new String("10")  );

	BlackJackFrame(){
		setMenu();
		loadPlayer();
		dealer = new Dealer( this );
		trip.setBet(10);		//Start Game at $10 bets
		setTable();
	}

	public void setTable(){
		setTitle("OX BlackJack Table");
		setSize(500,350);
		setLocation(200,200);
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		getContentPane().setLayout( new GridLayout(4,0) );
		setJPanelData();
		getContentPane().add(jPanelData);
		setJPanelDealer();
		getContentPane().add(jPanelDealer);
		setJPanelPlayer();
		getContentPane().add(jPanelPlayer);
		setJPanelControls();
		getContentPane().add(jPanelControls);
		setVisible(true);

		addKeyListener( new KeyAdapter(){
			public void keyTyped( KeyEvent e ){
				if( e.getKeyCode() == KeyEvent.VK_D ){
					deal();
				}
			}
		});

/*		addWindowListener( new WindowAdapter(){
			public void WindowClosed( WindowEvent e ){
				exitGame();
			}
			public void WindowClosing( WindowEvent e ){
				exitGame();
			}
		});
*/	}

	public void setJPanelData(){
		jPanelData = new JPanel();
		jPanelData.setLayout( new GridLayout(2,0) );
		/////
		Box boxCash = new Box( BoxLayout.Y_AXIS );
		jLabelGameWinnings = new JLabel("Game Winnings");
		boxCash.add(jLabelGameWinnings);
		jTextFieldGameWinnings = new JTextField(7);
		jTextFieldGameWinnings.setHorizontalAlignment(JTextField.RIGHT);
		jTextFieldGameWinnings.setEditable(false);
		setWinnings();
		boxCash.add(jTextFieldGameWinnings);
		JPanel jPanelTopData = new JPanel();
		jPanelTopData.add( boxCash );
		jPanelData.add( jPanelTopData );
		/////
		jProgressBarDeck = new JProgressBar(
			new DefaultBoundedRangeModel( 4*52, 0, 0, 4*52 ) ); //Default 4 decks
		jProgressBarDeck.setString(""+jProgressBarDeck.getValue());
		jProgressBarDeck.addChangeListener( new ChangeListener(){
			public void stateChanged( ChangeEvent e ){
				jProgressBarDeck.setString(""+jProgressBarDeck.getValue());
			}
		});
		jProgressBarDeck.setStringPainted( true );
		JPanel jPanelBottomData = new JPanel();
		jPanelBottomData.add(jProgressBarDeck);
		jPanelData.add(jPanelBottomData);
	}

	public void setJPanelDealer(){
		jPanelDealer = new BlackJackTable( dealer.dealerCards );
		//jPanelDealer.setLayout( new CardLayout() );
		jPanelDealer.setLayout( new FlowLayout() );
///		jPanelDealer.add( c1 );
	}

	public void setJPanelPlayer(){
		jPanelPlayer = new BlackJackTable( dealer.playerCards );
		//jPanelPlayer.setLayout( new CardLayout() );
		jPanelPlayer.setLayout( new FlowLayout() );
///		jPanelPlayer.add( c2 );
///		jPanelPlayer.add( new Card( 14, 0 ));
	}

	public void setJPanelControls(){
		jPanelControls.setLayout( new GridLayout(2,0) );
		/////
		JPanel jPanelDisplay = new JPanel();
		jLabelPlayer = new JLabel("Player");
		jPanelDisplay.add(jLabelPlayer);
		jTextFieldPlayer = new JTextField(playerName.length());
		jTextFieldPlayer.setText(playerName);
		jTextFieldPlayer.setEditable(false);
		jPanelDisplay.add(jTextFieldPlayer);
		jLabelCash = new JLabel("Cash");
		jPanelDisplay.add(jLabelCash);
		jTextFieldCash = new JTextField(7);
		jTextFieldCash.setHorizontalAlignment(JTextField.RIGHT);
		jTextFieldCash.setText("$"+trip.getCashOnHand());
		jTextFieldCash.setEditable(false);
		jPanelDisplay.add(jTextFieldCash);
		jLabelBet = new JLabel("Bet");
		jPanelDisplay.add(jLabelBet);
		jTextFieldBet = new JTextField(5);
		jPanelDisplay.add(jTextFieldBet);
		setBet();
		image = new ResultImage();
		image.setSize(18,26);
		jPanelDisplay.add(image);
		jPanelControls.add(jPanelDisplay);
		/////
		JPanel jPanelButtons = new JPanel();
		jPanelButtons.setLayout( new FlowLayout() );
		jButtonSplit = new JButton("Split");
		jPanelButtons.add(jButtonSplit);
		jButtonDouble = new JButton("Double");
		jPanelButtons.add(jButtonDouble);
		jButtonStay = new JButton("Stay");
		jPanelButtons.add(jButtonStay);
		jButtonHit = new JButton("Hit");
		jPanelButtons.add(jButtonHit);
		jButtonDeal = new JButton("Deal");
		jPanelButtons.add(jButtonDeal);
		setActiveButtons();
		jPanelControls.add(jPanelButtons);

		jButtonDeal.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				deal();
			}
		});

		jButtonHit.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				dealer.hit();
				setActiveButtons();
			}
		});

		jButtonStay.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				dealer.stay();
				setActiveButtons();
			}
		});

		jButtonDouble.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				dealer.doublePlay();
				setActiveButtons();
			}
		});

		jButtonSplit.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				dealer.split();
				setActiveButtons();
			}
		});
	}

	private void setActiveButtons(){
		if( dealer.state == dealer.BID ){
			jButtonDeal.setEnabled(true);
			jMenuItemDeal.setEnabled(true);
			jButtonHit.setEnabled(false);
			jButtonStay.setEnabled(false);
			jButtonDouble.setEnabled(false);
			jButtonSplit.setEnabled(false);
			jTextFieldBet.setEditable(true);
		}else if ( dealer.state == dealer.PLAY ){
			jButtonDeal.setEnabled(false);
			jMenuItemDeal.setEnabled(false);
			jButtonHit.setEnabled(true);
			jButtonStay.setEnabled(true);
			if(dealer.playerCards.size() < 3){
				jButtonDouble.setEnabled(true);
				jButtonSplit.setEnabled(true);
			}else{
				jButtonDouble.setEnabled(false);//if first play else setEnabled(false)
				jButtonSplit.setEnabled(false);//if first play else setEnabled(false)
			}
			jTextFieldBet.setEditable(false);
		}
		if( trip.getCashOnHand() < 10 ){
			JOptionPane.showMessageDialog( this, "You have NO MORE MONEY left.\nPlay again Later!",
				"OX SAYS GET OTTA MY CASINO", JOptionPane.INFORMATION_MESSAGE );
				exitGame();
		}
	}

	public void setMenu(){
		jMenuBarBJ = new JMenuBar();

		JMenu jMenuGame = new JMenu("Game");
		jMenuItemDeal = new JMenuItem("Deal");
		jMenuGame.add(jMenuItemDeal);
		JMenuItem jMenuItemShuffle = new JMenuItem("Shuffle");
		jMenuGame.add(jMenuItemShuffle);
		JMenuItem jMenuItemChangePlayer = new JMenuItem("Change Player");
		jMenuGame.add(jMenuItemChangePlayer);
		jMenuGame.addSeparator();
		JMenuItem jMenuItemExit = new JMenuItem("Exit");
		jMenuGame.add(jMenuItemExit);

		JMenu jMenuOptions = new JMenu("Options");
		JMenuItem jMenuDecks = new JMenu("NumberOfDecks");
		JMenuItem jMenuItemDisplayStatistics = new JMenuItem("Display Statistics");
		jMenuOptions.add(jMenuItemDisplayStatistics);
		JCheckBoxMenuItem jMenuItemOne = new JCheckBoxMenuItem("1");
		jMenuDecks.add(jMenuItemOne);
		JCheckBoxMenuItem jMenuItemTwo = new JCheckBoxMenuItem("2");
		jMenuDecks.add(jMenuItemTwo);
		JCheckBoxMenuItem jMenuItemFour = new JCheckBoxMenuItem("4", true);
		jMenuDecks.add(jMenuItemFour);
		jMenuOptions.add(jMenuDecks);
		jMenuItemShowDeck = new JCheckBoxMenuItem("Show Cards in Deck", true);
		jMenuOptions.add(jMenuItemShowDeck);

		jMenuGame.setMnemonic('G');
		jMenuItemDeal.setMnemonic('D');
		jMenuItemShuffle.setMnemonic('S');
		jMenuItemChangePlayer.setMnemonic('C');
		jMenuItemExit.setMnemonic('x');
		jMenuOptions.setMnemonic('O');
		jMenuItemDisplayStatistics.setMnemonic('S');
		jMenuDecks.setMnemonic('N');
		jMenuItemShowDeck.setMnemonic('C');

		ButtonGroup bg = new ButtonGroup();
		bg.add(jMenuItemOne);
		bg.add(jMenuItemTwo);
		bg.add(jMenuItemFour);

		jMenuBarBJ.add(jMenuGame);
		jMenuBarBJ.add(jMenuOptions);
		setJMenuBar(jMenuBarBJ);

		jMenuItemDeal.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				deal();
			}
		});
		jMenuItemShuffle.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				dealer.shuffle();
			}
		});
		jMenuItemChangePlayer.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				changePlayer();
			}
		});
		jMenuItemExit.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				exitGame();
			}
		});
		jMenuItemDisplayStatistics.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				StatisticsWindow stats = new StatisticsWindow();
				stats.setData( trip );
				stats.setSize( 170, 300 );
				stats.setLocation( 300, 150 );
				stats.show();
				//stats.setVisible(true);
			}
		});
		jMenuItemOne.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				dealer.setNumberOfDecks( 1 );
				((BoundedRangeModel)jProgressBarDeck.getModel()).setRangeProperties(
					1*52, 0, 0, 1*52, false);
			}
		});
		jMenuItemTwo.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				dealer.setNumberOfDecks( 2 );
				((BoundedRangeModel)jProgressBarDeck.getModel()).setRangeProperties(
					2*52, 0, 0, 2*52, false );
			}
		});
		jMenuItemFour.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				dealer.setNumberOfDecks( 4 );
				((BoundedRangeModel)jProgressBarDeck.getModel()).setRangeProperties(
					4*52, 0, 0, 4*52, false );
			}
		});
		jMenuItemShowDeck.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				if(jMenuItemShowDeck.getState()){
					jProgressBarDeck.setVisible(true);
				}else{
					jProgressBarDeck.setVisible(false);
				}
			}
		});
	}

	private String getPlayerName(){
		playerName = JOptionPane.showInputDialog(this, "Player Name",
			"Enter your name",JOptionPane.QUESTION_MESSAGE);
		if((playerName == null)||(playerName.length() < 1)){
			playerName = "Default";
		}
		return playerName;
	}

	private void loadPlayer(){
		trip = new TripToCasino( getPlayerName() );
	}

	private void changePlayer(){
		loadPlayer();
		jTextFieldPlayer.setText(playerName);
	}

	private void setWinnings(){
		jTextFieldGameWinnings.setText(""+trip.tripWinnings());
	}

	public void setMoneyField( JTextField moneyField, int money ){
		if( money >= 0 ){
			moneyField.setForeground( Color.black );
			moneyField.setText( "$" + money );
		}else{
			moneyField.setForeground( Color.black );
			moneyField.setText( "-$" + Math.abs(money) );
		}
	}

	private void setBet(){
		jTextFieldBet.setText(""+trip.getBet());
	}

	private void deal(){
		if( isValidBet() ){
			trip.setBet( new Integer(
				jTextFieldBet.getText().trim()).intValue() );
			dealer.deal();
			setActiveButtons();
		}else{
			invalidBet();
		}
		jPanelDealer.repaint();
		jPanelPlayer.repaint();
	}

	private boolean isValidBet(){
		try{
			if( new Integer(jTextFieldBet.getText().trim()).intValue()%10 == 0 ){
				return true;
			}
		}catch( NumberFormatException nfe ){
			System.out.println("Bet is not a valid number");

	}
		return false;
	}

	private void invalidBet(){
		JOptionPane.showMessageDialog(null,
			"Bet must be a multiple of 10", "INVALID BET",
			JOptionPane.ERROR_MESSAGE);
		jTextFieldBet.requestFocus();
	}

	public void setSums(){
		jPanelDealer.sum.setText( ""+dealer.calculateSum(dealer.dealerCards) );
		jPanelPlayer.sum.setText( ""+dealer.calculateSum(dealer.playerCards) );
	}

	private void exitGame(){
		dispose();
		System.exit(0);
	}
}