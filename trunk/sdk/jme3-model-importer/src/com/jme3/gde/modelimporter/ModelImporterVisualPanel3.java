/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.modelimporter;

import com.jme3.asset.AssetKey;
import com.jme3.gde.core.assets.AssetData;
import com.jme3.gde.core.assets.ProjectAssetManager;
import com.jme3.gde.core.scene.OffScenePanel;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.List;
import javax.swing.JPanel;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.util.Exceptions;

@SuppressWarnings({"unchecked", "serial"})
public final class ModelImporterVisualPanel3 extends JPanel {

    private ModelImporterWizardPanel3 panel;
    private OffScenePanel offPanel;
    private ProjectAssetManager manager;
    private AssetData data;
    private AssetKey mainKey;
    private Spatial currentModel;
    private List<AssetKey> assets;
    private List<AssetKey> failed;

    /**
     * Creates new form ModelImporterVisualPanel1
     */
    public ModelImporterVisualPanel3(ModelImporterWizardPanel3 panel) {
        initComponents();
        this.panel = panel;
        offPanel = new OffScenePanel(320, 320);
        offPanel.startPreview();
        jPanel1.add(offPanel);
    }

    @Override
    public String getName() {
        return "Preview Model";
    }

    public void loadSettings(WizardDescriptor wiz) {
        manager = (ProjectAssetManager) wiz.getProperty("manager");
        mainKey = (AssetKey) wiz.getProperty("mainkey");
        data = (AssetData) wiz.getProperty("assetdata");
        loadModel(mainKey);
        if (currentModel != null) {
            offPanel.attach(currentModel);
        }
    }

    public void applySettings(WizardDescriptor wiz) {
        wiz.putProperty("assetlist", assets);
        wiz.putProperty("failedlist", failed);
        wiz.putProperty("model", currentModel);
        if (currentModel != null) {
            offPanel.detach(currentModel);
        }
    }

    public boolean checkValid() {
        return currentModel != null;
    }

    public void cleanup() {
        if (currentModel != null) {
            offPanel.detach(currentModel);
        }
        offPanel.stopPreview();
    }

    public synchronized void loadModel(AssetKey modelKey) {
        try {
            currentModel = (Spatial) data.loadAsset();
            if (currentModel != null) {
                assets = data.getAssetKeyList();
                failed = data.getFailedList();
                jList1.setListData(assets.toArray());
                jList2.setListData(failed.toArray());
                if (failed.size() > 0) {
                    statusLabel.setText("Model loaded, some assets could not be loaded.");
                    infoTextArea.setText("Check if the model looks as expected,"
                            + " some textures fail to load, make sure they are in the same folder or a subfolder of the model."
                            + " Textures that can not be found will be replaced with a red material."
                            + "\nWith the buttons above you can move the camera.");
                } else {
                    statusLabel.setText("Model loaded successfully.");
                    infoTextArea.setText("Check if the model looks as expected."
                            + "\nWith the buttons above you can move the camera.");
                }
            } else {
                statusLabel.setText("Model cannot be loaded.");
                infoTextArea.setText("This file can not be loaded, it is either in an unsupported format or is incompatible."
                        + "\n Check the bottom right corner of the SDK for a little warning sign. If it is there"
                        + " double-click it and report the contained stack trace at jmonkeyengine.org");
//                Message msg = new NotifyDescriptor.Message(
//                        "Cannot import this file!",
//                        NotifyDescriptor.ERROR_MESSAGE);
//                DialogDisplayer.getDefault().notifyLater(msg);
            }
        } catch (Exception e) {
            statusLabel.setText("Error importing file");
            NotifyDescriptor.Message msg = new NotifyDescriptor.Message(
                    "Error importing file!\n"
                    + "(" + e + ")",
                    NotifyDescriptor.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notifyLater(msg);
            Exceptions.printStackTrace(e);
        }
        manager.clearCache();
        panel.fireChangeEvent();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        statusLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        infoTextArea = new javax.swing.JTextArea();

        jPanel1.setPreferredSize(new java.awt.Dimension(320, 320));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(ModelImporterVisualPanel3.class, "ModelImporterVisualPanel3.jButton1.text")); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(ModelImporterVisualPanel3.class, "ModelImporterVisualPanel3.jButton2.text")); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);
        jToolBar1.add(jSeparator1);

        org.openide.awt.Mnemonics.setLocalizedText(jButton3, org.openide.util.NbBundle.getMessage(ModelImporterVisualPanel3.class, "ModelImporterVisualPanel3.jButton3.text")); // NOI18N
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        org.openide.awt.Mnemonics.setLocalizedText(jButton4, org.openide.util.NbBundle.getMessage(ModelImporterVisualPanel3.class, "ModelImporterVisualPanel3.jButton4.text")); // NOI18N
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        jToolBar1.add(jPanel3);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(ModelImporterVisualPanel3.class, "ModelImporterVisualPanel3.jLabel2.text")); // NOI18N

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "No assets loaded" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        jScrollPane3.setViewportView(jList2);

        statusLabel.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(statusLabel, org.openide.util.NbBundle.getMessage(ModelImporterVisualPanel3.class, "ModelImporterVisualPanel3.statusLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ModelImporterVisualPanel3.class, "ModelImporterVisualPanel3.jLabel1.text_1")); // NOI18N

        infoTextArea.setColumns(20);
        infoTextArea.setLineWrap(true);
        infoTextArea.setRows(5);
        infoTextArea.setText(org.openide.util.NbBundle.getMessage(ModelImporterVisualPanel3.class, "ModelImporterVisualPanel3.infoTextArea.text_1")); // NOI18N
        infoTextArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(infoTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        offPanel.zoomCamera(-.1f);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        offPanel.zoomCamera(.1f);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        offPanel.rotateCamera(Vector3f.UNIT_Y, .1f);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        offPanel.rotateCamera(Vector3f.UNIT_Y, -.1f);
    }//GEN-LAST:event_jButton4ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea infoTextArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel statusLabel;
    // End of variables declaration//GEN-END:variables
}
