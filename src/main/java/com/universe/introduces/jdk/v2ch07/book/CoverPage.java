package com.universe.introduces.jdk.v2ch07.book;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.print.*;
import javax.swing.*;

/**
 * This class prints a cover page with a title.
 */
public class CoverPage implements Printable
{
   private String title;

   /**
    * Constructs a cover page.
    * @param t the title
    */
   public CoverPage(String t)
   {
      title = t;
   }

   public int print(Graphics g, PageFormat pf, int page) throws PrinterException
   {
      if (page >= 1) return Printable.NO_SUCH_PAGE;
      Graphics2D g2 = (Graphics2D) g;
      g2.setPaint(Color.black);
      g2.translate(pf.getImageableX(), pf.getImageableY());
      FontRenderContext context = g2.getFontRenderContext();
      Font f = g2.getFont();
      TextLayout layout = new TextLayout(title, f, context);
      float ascent = layout.getAscent();
      g2.drawString(title, 0, ascent);
      return Printable.PAGE_EXISTS;
   }
}

/**
 * This class implements a generic print preview dialog.
 */
class PrintPreviewDialog extends JDialog
{
   private static final int DEFAULT_WIDTH = 300;
   private static final int DEFAULT_HEIGHT = 300;

   private PrintPreviewCanvas canvas;

   /**
    * Constructs a print preview dialog.
    * @param p a Printable
    * @param pf the page format
    * @param pages the number of pages in p
    */
   public PrintPreviewDialog(Printable p, PageFormat pf, int pages)
   {
      Book book = new Book();
      book.append(p, pf, pages);
      layoutUI(book);
   }

   /**
    * Constructs a print preview dialog.
    * @param b a Book
    */
   public PrintPreviewDialog(Book b)
   {
      layoutUI(b);
   }

   /**
    * Lays out the UI of the dialog.
    * @param book the book to be previewed
    */
   public void layoutUI(Book book)
   {
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      canvas = new PrintPreviewCanvas(book);
      add(canvas, BorderLayout.CENTER);

      JPanel buttonPanel = new JPanel();

      JButton nextButton = new JButton("Next");
      buttonPanel.add(nextButton);
      nextButton.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               canvas.flipPage(1);
            }
         });

      JButton previousButton = new JButton("Previous");
      buttonPanel.add(previousButton);
      previousButton.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               canvas.flipPage(-1);
            }
         });

      JButton closeButton = new JButton("Close");
      buttonPanel.add(closeButton);
      closeButton.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               setVisible(false);
            }
         });

      add(buttonPanel, BorderLayout.SOUTH);
   }
}

