/*******************************************************************************
 * Copyright (c) 2009, 2011 Overture Team and others.
 *
 * Overture is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Overture is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Overture.  If not, see <http://www.gnu.org/licenses/>.
 * 	
 * The Overture Tool web-site: http://overturetool.org/
 *******************************************************************************/
//package org.overture.ide.debug.internal.ui.views.launch;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.Iterator;
//
//import org.eclipse.core.runtime.IAdaptable;
//import org.eclipse.core.runtime.IProgressMonitor;
//import org.eclipse.core.runtime.IStatus;
//import org.eclipse.core.runtime.MultiStatus;
//import org.eclipse.core.runtime.Status;
//import org.eclipse.core.runtime.jobs.Job;
//import org.eclipse.debug.core.DebugException;
//import org.eclipse.debug.core.DebugPlugin;
//import org.eclipse.debug.core.ILaunch;
//import org.eclipse.debug.core.ILaunchManager;
//import org.eclipse.debug.core.model.IDebugElement;
//import org.eclipse.debug.core.model.IDebugTarget;
//import org.eclipse.debug.core.model.IProcess;
//import org.eclipse.debug.core.model.IStackFrame;
//import org.eclipse.debug.core.model.ITerminate;
//import org.eclipse.debug.internal.ui.DebugUIPlugin;
//import org.eclipse.debug.internal.ui.DelegatingModelPresentation;
//import org.eclipse.debug.internal.ui.IDebugHelpContextIds;
//import org.eclipse.debug.internal.ui.actions.AddToFavoritesAction;
//import org.eclipse.debug.internal.ui.actions.EditLaunchConfigurationAction;
//import org.eclipse.debug.internal.ui.commands.actions.DebugCommandAction;
//import org.eclipse.debug.internal.ui.commands.actions.DisconnectCommandAction;
//import org.eclipse.debug.internal.ui.commands.actions.DropToFrameCommandAction;
//import org.eclipse.debug.internal.ui.commands.actions.ResumeCommandAction;
//import org.eclipse.debug.internal.ui.commands.actions.StepIntoCommandAction;
//import org.eclipse.debug.internal.ui.commands.actions.StepOverCommandAction;
//import org.eclipse.debug.internal.ui.commands.actions.StepReturnCommandAction;
//import org.eclipse.debug.internal.ui.commands.actions.SuspendCommandAction;
//import org.eclipse.debug.internal.ui.commands.actions.TerminateAllAction;
//import org.eclipse.debug.internal.ui.commands.actions.TerminateAndRelaunchAction;
//import org.eclipse.debug.internal.ui.commands.actions.TerminateAndRemoveAction;
//import org.eclipse.debug.internal.ui.commands.actions.TerminateCommandAction;
//import org.eclipse.debug.internal.ui.commands.actions.ToggleStepFiltersAction;
//import org.eclipse.debug.internal.ui.preferences.IDebugPreferenceConstants;
//import org.eclipse.debug.internal.ui.sourcelookup.EditSourceLookupPathAction;
//import org.eclipse.debug.internal.ui.sourcelookup.LookupSourceAction;
//import org.eclipse.debug.internal.ui.viewers.model.InternalTreeModelViewer;
//import org.eclipse.debug.internal.ui.viewers.model.VirtualFindAction;
//import org.eclipse.debug.internal.ui.viewers.model.provisional.IModelChangedListener;
//import org.eclipse.debug.internal.ui.viewers.model.provisional.IModelDelta;
//import org.eclipse.debug.internal.ui.viewers.model.provisional.IModelDeltaVisitor;
//import org.eclipse.debug.internal.ui.viewers.model.provisional.IModelProxy;
//import org.eclipse.debug.internal.ui.viewers.model.provisional.IViewerUpdate;
//import org.eclipse.debug.internal.ui.viewers.model.provisional.IViewerUpdateListener;
//import org.eclipse.debug.internal.ui.viewers.model.provisional.TreeModelViewer;
//import org.eclipse.debug.internal.ui.views.DebugModelPresentationContext;
//import org.eclipse.debug.internal.ui.views.DebugUIViewsMessages;
//
//
//// import org.eclipse.debug.internal.ui.views.launch.LaunchView.TreeViewerContextProvider;
//// import org.eclipse.debug.internal.ui.views.launch.LaunchView.TreeViewerContextProvider.Visitor;
//import org.eclipse.debug.ui.AbstractDebugView;
//import org.eclipse.debug.ui.DebugUITools;
//import org.eclipse.debug.ui.IDebugModelPresentation;
//import org.eclipse.debug.ui.IDebugUIConstants;
//import org.eclipse.debug.ui.contexts.AbstractDebugContextProvider;
//import org.eclipse.debug.ui.contexts.DebugContextEvent;
//import org.eclipse.debug.ui.contexts.IDebugContextListener;
//import org.eclipse.debug.ui.contexts.IDebugContextProvider;
//import org.eclipse.jface.action.GroupMarker;
//import org.eclipse.jface.action.IMenuListener;
//import org.eclipse.jface.action.IMenuManager;
//import org.eclipse.jface.action.IToolBarManager;
//import org.eclipse.jface.action.MenuManager;
//import org.eclipse.jface.action.Separator;
//import org.eclipse.jface.dialogs.MessageDialog;
//import org.eclipse.jface.preference.IPreferenceStore;
//import org.eclipse.jface.viewers.DoubleClickEvent;
//import org.eclipse.jface.viewers.ISelection;
//import org.eclipse.jface.viewers.ISelectionChangedListener;
//import org.eclipse.jface.viewers.IStructuredSelection;
//import org.eclipse.jface.viewers.ITreeSelection;
//import org.eclipse.jface.viewers.SelectionChangedEvent;
//import org.eclipse.jface.viewers.StructuredViewer;
//import org.eclipse.jface.viewers.TreePath;
//import org.eclipse.jface.viewers.TreeSelection;
//import org.eclipse.jface.viewers.Viewer;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.ControlEvent;
//import org.eclipse.swt.events.ControlListener;
//import org.eclipse.swt.events.KeyAdapter;
//import org.eclipse.swt.events.KeyEvent;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Control;
//import org.eclipse.ui.IActionBars;
//import org.eclipse.ui.IMemento;
//import org.eclipse.ui.IPageListener;
//import org.eclipse.ui.IPartListener2;
//import org.eclipse.ui.IPerspectiveDescriptor;
//import org.eclipse.ui.IPerspectiveListener2;
//import org.eclipse.ui.IViewSite;
//import org.eclipse.ui.IWorkbenchActionConstants;
//import org.eclipse.ui.IWorkbenchPage;
//import org.eclipse.ui.IWorkbenchPart;
//import org.eclipse.ui.IWorkbenchPartReference;
//import org.eclipse.ui.IWorkbenchWindow;
//import org.eclipse.ui.PartInitException;
//import org.eclipse.ui.WorkbenchException;
//import org.eclipse.ui.XMLMemento;
//import org.eclipse.ui.actions.SelectionListenerAction;
//import org.eclipse.ui.dialogs.PropertyDialogAction;
//import org.eclipse.ui.part.IPageBookViewPage;
//import org.eclipse.ui.part.IPageSite;
//import org.eclipse.ui.part.IShowInSource;
//import org.eclipse.ui.part.IShowInTarget;
//import org.eclipse.ui.part.IShowInTargetList;
//import org.eclipse.ui.part.Page;
//import org.eclipse.ui.part.ShowInContext;
//
//import org.eclipse.ui.progress.IWorkbenchSiteProgressService;
//import org.eclipse.ui.progress.UIJob;
//
//@SuppressWarnings( { "restriction", "unchecked" })
//public class VdmLaunchView extends AbstractDebugView implements ISelectionChangedListener, IPerspectiveListener2, IPageListener, IShowInTarget, IShowInSource, IShowInTargetList, IPartListener2, IViewerUpdateListener {
//	
//	public static final String ID_CONTEXT_ACTIVITY_BINDINGS = "contextActivityBindings"; //$NON-NLS-1$
//
//    private static final String TERMINATE = "terminate"; //$NON-NLS-1$
//
//    private static final String DISCONNECT = "disconnect"; //$NON-NLS-1$
//
//    private static final String SUSPEND = "suspend"; //$NON-NLS-1$
//
//    private static final String RESUME = "resume"; //$NON-NLS-1$
//
//    private static final String STEP_RETURN = "step_return"; //$NON-NLS-1$
//
//    private static final String STEP_OVER = "step_over"; //$NON-NLS-1$
//
//    private static final String DROP_TO_FRAME = "drop_to_frame"; //$NON-NLS-1$
//
//    private static final String STEP_INTO = "step_into"; //$NON-NLS-1$
//
//    private static final String TERMINATE_AND_REMOVE = "terminate_and_remove"; //$NON-NLS-1$
//    
//    private static final String TERMINATE_ALL = "terminate_all"; //$NON-NLS-1$
//
//    private static final String TERMINATE_AND_RELAUNCH = "terminate_relaunch"; //$NON-NLS-1$
//    
//    private static final String TOGGLE_STEP_FILTERS = "toggle_step_filters"; //$NON-NLS-1$
//			
//    private static final int BREADCRUMB_TRIGGER_RANGE = 5; // pixels
//    
//    private static final int BREADCRUMB_STICKY_RANGE = 20; // pixels
//    
//	/**
//	 * Whether this view is in the active page of a perspective.
//	 */
//	private boolean fIsActive = true; 	
//		
//	/**
//	 * Model presentation or <code>null</code> if none
//	 */
//	private IDebugModelPresentation fPresentation = null;
//	
//	private EditLaunchConfigurationAction fEditConfigAction = null;
//	private AddToFavoritesAction fAddToFavoritesAction = null;
//	private EditSourceLookupPathAction fEditSourceAction = null;
//	private LookupSourceAction fLookupAction = null;
//
//	/**
//	 * Current view mode (auto vs. breadcrumb, vs. tree view).  
//	 * 
//	 * @since 3.5
//	 */
//    private String fCurrentViewMode = IDebugPreferenceConstants.DEBUG_VIEW_MODE_AUTO;
//    
//    /**
//     * Actions for selecting the view mode (auto vs. breadcrumb, vs. tree view).
//     * 
//     * @since 3.5
//     */
//    private DebugViewModeAction[] fDebugViewModeActions;
//
//    /**
//     * Action that controls the breadcrumb drop-down auto-expand behavior.
//     * 
//     * @since 3.5
//     */
//   // private BreadcrumbDropDownAutoExpandAction fBreadcrumbDropDownAutoExpandAction;
//    
//    /**
//     * Preference name for the view's memento.
//     * 
//     * @since 3.5
//     */
//    private String PREF_STATE_MEMENTO = "pref_state_memento."; //$NON-NLS-1$
//
//    /**
//     * Key for a view preference for whether the elements in breadcrumb's
//     * drop-down viewer should be automatically expanded.
//     * 
//     * @since 3.5
//     */
//    private static final String BREADCRUMB_DROPDOWN_AUTO_EXPAND = DebugUIPlugin.getUniqueIdentifier() + ".BREADCRUMB_DROPDOWN_AUTO_EXPAND"; //$NON-NLS-1$
//    
//    /**
//     * Preference for whether the elements in breadcrumb's
//     * drop-down viewer should be automatically expanded.
//     * 
//     * @since 3.5
//     */
//    private boolean fBreadcrumbDropDownAutoExpand = false;
//    
//	/**
//	 * Page-book page for the breadcrumb viewer.  This page is activated in 
//	 * Debug view when the height of the view is reduced to just one line. 
//     * 
//     * @since 3.5
//	 */
//	private class BreadcrumbPage extends Page {
//
////	    LaunchViewBreadcrumb fCrumb;
//	    Control fControl;
//
//	    public void createControl(Composite parent) {
////	        fCrumb = new LaunchViewBreadcrumb(LaunchView.this, (TreeModelViewer)getViewer(), fTreeViewerDebugContextProvider);
////	        fControl = fCrumb.createContent(parent);
//	    }
//
//	    public void init(IPageSite pageSite) {
//	        super.init(pageSite);
////            pageSite.setSelectionProvider(fCrumb.getSelectionProvider());
//	    }
//	    
//	    public Control getControl() {
//	        return fControl;
//	    }
//
//	    public void setFocus() {
////	        fCrumb.activate();
//	    }
//	    
//	    IDebugContextProvider getContextProvider() {
////	        return fCrumb.getContextProvider();
//	    	return null;
//	    }
//
//	    int getHeight() {
////	        return fCrumb.getHeight();
//	    	return 0;
//	    }
//	}
//
////	private BreadcrumbPage fBreadcrumbPage;
//	
//	class TreeViewerContextProvider extends AbstractDebugContextProvider implements IModelChangedListener {
//		
//		private ISelection fContext = null;
//		private TreeModelViewer fViewer = null;
//		private Visitor fVisitor = new Visitor();
//		
//		class Visitor implements IModelDeltaVisitor {
//			public boolean visit(IModelDelta delta, int depth) {
//				if ((delta.getFlags() & (IModelDelta.STATE | IModelDelta.CONTENT)) > 0) {
//					// state and/or content change
//					if ((delta.getFlags() & IModelDelta.SELECT) == 0) {
//						// no select flag
//						if ((delta.getFlags() & IModelDelta.CONTENT) > 0) {
//							// content has changed without select >> possible re-activation
//							possibleChange(getViewerTreePath(delta), DebugContextEvent.ACTIVATED);
//						} else if ((delta.getFlags() & IModelDelta.STATE) > 0) {
//							// state has changed without select >> possible state change of active context
//							possibleChange(getViewerTreePath(delta), DebugContextEvent.STATE);
//						}
//					}
//				}
//				return true;
//			}	
//		}
//		
//		/**
//		 * Returns a tree path for the node, *not* including the root element.
//		 * 
//		 * @param node
//		 *            model delta
//		 * @return corresponding tree path
//		 */
//		private TreePath getViewerTreePath(IModelDelta node) {
//			ArrayList list = new ArrayList();
//			IModelDelta parentDelta = node.getParentDelta();
//			while (parentDelta != null) {
//				list.add(0, node.getElement());
//				node = parentDelta;
//				parentDelta = node.getParentDelta();
//			}
//			return new TreePath(list.toArray());
//		}
//		
//		public TreeViewerContextProvider(TreeModelViewer viewer) {
//			super(VdmLaunchView.this);
//			fViewer = viewer;
//			fViewer.addModelChangedListener(this);
//		}
//		
//		protected void dispose() { 
//			fContext = null;
//			fViewer.removeModelChangedListener(this);
//		}
//		
//		/* (non-Javadoc)
//		 * @see org.eclipse.debug.ui.contexts.IDebugContextProvider#getActiveContext()
//		 */
//		public synchronized ISelection getActiveContext() {
//			return fContext;
//		}	
//		
//		protected void activate(ISelection selection) {
//			synchronized (this) {
//				fContext = selection;
//			}
//			fire(new DebugContextEvent(this, selection, DebugContextEvent.ACTIVATED));
//		}
//		
//		protected void possibleChange(TreePath element, int type) {
//			DebugContextEvent event = null;
//			synchronized (this) {
//				if (fContext instanceof ITreeSelection) {
//					ITreeSelection ss = (ITreeSelection) fContext;
//					if (ss.size() == 1) {
//						TreePath current = ss.getPaths()[0];
//						if (current.startsWith(element, null)) {
//							if (current.getSegmentCount() == element.getSegmentCount()) {
//								event = new DebugContextEvent(this, fContext, type);
//							} else {
//							    // if parent of the currently selected element 
//							    // changes, issue event to update STATE only
//                               event = new DebugContextEvent(this, fContext, DebugContextEvent.STATE);
//							}
//						}
//					}
//				} 
//			}
//			if (event == null) {
//				return;
//			}
//			if (getControl().getDisplay().getThread() == Thread.currentThread()) {
//				fire(event);
//			} else {
//				final DebugContextEvent finalEvent = event;
//				Job job = new UIJob("context change") { //$NON-NLS-1$
//					public IStatus runInUIThread(IProgressMonitor monitor) {
//						// verify selection is still the same context since job was scheduled
//						synchronized (TreeViewerContextProvider.this) {
//							if (fContext instanceof IStructuredSelection) {
//								IStructuredSelection ss = (IStructuredSelection) fContext;
//								Object changed = ((IStructuredSelection)finalEvent.getContext()).getFirstElement();
//								if (!(ss.size() == 1 && ss.getFirstElement().equals(changed))) {
//									return Status.OK_STATUS;
//								}
//							}
//						}
//						fire(finalEvent);
//						return Status.OK_STATUS;
//					}
//				};
//				job.setSystem(true);
//				job.schedule();
//			}
//		}
//
//		/* (non-Javadoc)
//		 * @see org.eclipse.debug.internal.ui.viewers.model.provisional.IModelChangedListener#modelChanged(org.eclipse.debug.internal.ui.viewers.model.provisional.IModelDelta)
//		 */
//		public void modelChanged(IModelDelta delta, IModelProxy proxy) {
//			delta.accept(fVisitor);
//		}
//		
//	}
//	
//	/**
//	 * Context provider
//	 */
//	private TreeViewerContextProvider fTreeViewerDebugContextProvider;
//
//	/**
//	 * The PageBookView, which is a base class of this class does not make it 
//	 * easy to control which page is currently active.  It is intended that the 
//	 * page book pages are associated with workbench parts, and the parts are 
//	 * in turn associated with PageRec records.  
//	 * <p>
//	 * PageRec is needed in order to properly active a page book page, by 
//	 * calling showPageRec(), so in this class we need to add some hooks in
//	 * order to obtain the page record for the tree viewer page and the 
//	 * breadcrumb page.</p><p> 
//	 * For the default page, we override the showPageRec() 
//	 * to determine if the default page is being shown and if it is, we save
//	 * its record for later use.  showPageRec() is always called for the default
//	 * page after it is created.  For the breadcrumb page, we use the page book
//	 * view mechanism to create the page based on a workbench part, but we have 
//	 * to create a dummy part in order for this to work.
//	 * </p>    
//	 * <p>
//	 * See bug 262845 and 262870.
//	 * </p>
//	 * 
//	 * @see #createPartControl(Composite)
//	 * @see BreadcrumbWorkbenchPart
//	 * @eee #doCreatePage(IWorkbenchPart)
//	 * @see #isImportant(IWorkbenchPart)
//	 * @see #autoSelectViewPage(Composite)
//	 * @see #showTreeViewerPage()
//	 * @see #showBreadcrumbPage()
//	 */
//	private PageRec fDefaultPageRec = null;
//
//	private ISelectionChangedListener fTreeViewerSelectionChangedListener = new ISelectionChangedListener() {
//	    public void selectionChanged(SelectionChangedEvent event) {
//	        fTreeViewerDebugContextProvider.activate(event.getSelection());
//	    }
//	};
//
//	private class ContextProviderProxy extends AbstractDebugContextProvider implements IDebugContextListener {
//	    private IDebugContextProvider fActiveProvider;
//	    private IDebugContextProvider[] fProviders;
//	    
//	    ContextProviderProxy(IDebugContextProvider[] providers) {
//	        super(VdmLaunchView.this);
//	        fProviders = providers;
//	        fActiveProvider = providers[0];
//	        for (int i = 0; i < fProviders.length; i++) {
//	            fProviders[i].addDebugContextListener(this);
//	        }
//	    }
//	    
//	    void setActiveProvider(IDebugContextProvider provider) {
//            if (!provider.equals(fActiveProvider)) {
//    	        ISelection activeContext = getActiveContext();
//                fActiveProvider = provider;
//                ISelection newActiveContext = getActiveContext();
//    	        if (!activeContext.equals(newActiveContext)) {
//        	        fire(new DebugContextEvent(this, getActiveContext(), DebugContextEvent.ACTIVATED));
//    	        }
//            }
//	    }
//	    
//        public ISelection getActiveContext() {
//            ISelection activeContext = fActiveProvider.getActiveContext();
//            if (activeContext != null) {
//                return activeContext;
//            }
//            return TreeSelection.EMPTY;
//        }
//
//        public void debugContextChanged(DebugContextEvent event) {
//	        if (event.getSource().equals(fActiveProvider)) {
//	            fire(new DebugContextEvent(this, event.getContext(), event.getFlags()));
//	        }
//	    }
//        
//        void dispose() {
//            for (int i = 0; i < fProviders.length; i++) {
//                fProviders[i].removeDebugContextListener(this);
//            }
//            fProviders = null;
//            fActiveProvider = null;
//        }
//	}
//	
//	private ContextProviderProxy fContextProviderProxy;
//	
//	/* (non-Javadoc)
//	 * @see org.eclipse.debug.ui.AbstractDebugView#getHelpContextId()
//	 */
//	protected String getHelpContextId() {
//		return IDebugHelpContextIds.DEBUG_VIEW;
//	}
//	
//	/* (non-Javadoc)
//	 * @see org.eclipse.debug.ui.AbstractDebugView#createActions()
//	 */
//	protected void createActions() {
//		setAction("Properties", new PropertyDialogAction(getSite(), getSite().getSelectionProvider())); //$NON-NLS-1$
//		fEditConfigAction = new EditLaunchConfigurationAction();
//		fAddToFavoritesAction = new AddToFavoritesAction();
//	//TODO	fEditSourceAction = new EditSourceLookupPathAction(this);
//		//TODO	fLookupAction = new LookupSourceAction(this);
//		setAction(FIND_ACTION, new VirtualFindAction((InternalTreeModelViewer) getViewer())); 
//        
//        addCapabilityAction(new TerminateCommandAction(), TERMINATE);
//        addCapabilityAction(new DisconnectCommandAction(), DISCONNECT);
//        addCapabilityAction(new SuspendCommandAction(), SUSPEND);
//        addCapabilityAction(new ResumeCommandAction(), RESUME);
//        addCapabilityAction(new StepReturnCommandAction(), STEP_RETURN);
//        addCapabilityAction(new StepOverCommandAction(), STEP_OVER);
//        addCapabilityAction(new StepIntoCommandAction(), STEP_INTO);
//        addCapabilityAction(new DropToFrameCommandAction(), DROP_TO_FRAME);
//        addCapabilityAction(new TerminateAndRemoveAction(), TERMINATE_AND_REMOVE);
//        addCapabilityAction(new TerminateAndRelaunchAction(), TERMINATE_AND_RELAUNCH);
//        addCapabilityAction(new TerminateAllAction(), TERMINATE_ALL);
//        addCapabilityAction(new ToggleStepFiltersAction(), TOGGLE_STEP_FILTERS);
//	}
//	
//
//	/**
//	 * Initializes the action and associates it with the given id.
//	 * 
//	 * @param capability
//	 * @param actionID
//	 */
//	private void addCapabilityAction(DebugCommandAction capability, String actionID) {
//		capability.init(this);
//		setAction(actionID, capability);
//	}
//	
//	/**
//	 * Disposes the given action.
//	 * 
//	 * @param actionID
//	 */
//	private void disposeCommandAction(String actionID) {
//		DebugCommandAction action = (DebugCommandAction) getAction(actionID);
//		action.dispose();
//	}
//	
//	/**
// 	 * Override the default implementation to create the breadcrumb page.
//	 * 
//	 * @since 3.5
//	 * @see #fDefaultPageRec
//	 */	
//	public void createPartControl(final Composite parent) {
//	    super.createPartControl(parent);
//
//	    // Copy the global action handlers to the default page.
//	    setGlobalActionBarsToPage((IPageBookViewPage)getDefaultPage());
//
//	    // Add view as a selection listener to the site.
//	    getSite().getSelectionProvider().addSelectionChangedListener(this);
//	    
//	    // Set the tree viewer as the selection provider to the default page.  
//	    // The page book view handles switching the between page selection
//	    // providers as needed.
//	    ((IPageBookViewPage)getDefaultPage()).getSite().setSelectionProvider(getViewer());
//
//	    // Call the PageBookView part listener to indirectly create the breadcrumb page.
//	    // This call eventually calls doCreatePage() implemented below.
////	    partActivated(new BreadcrumbWorkbenchPart(getSite()));
//        
//        fContextProviderProxy = new ContextProviderProxy(
//            new IDebugContextProvider[] {fTreeViewerDebugContextProvider});
//        DebugUITools.getDebugContextManager().getContextService(getSite().getWorkbenchWindow()).addDebugContextProvider(fContextProviderProxy);
//
//	    // Create and configure actions for selecting view mode.
//        createViewModeActions(parent);
//        IPreferenceStore prefStore = DebugUIPlugin.getDefault().getPreferenceStore();
//        String mode = prefStore.getString(IDebugPreferenceConstants.DEBUG_VIEW_MODE);
//        setViewMode(mode, parent);
//        for (int i = 0; i < fDebugViewModeActions.length; i++) {
//            fDebugViewModeActions[i].setChecked(fDebugViewModeActions[i].getMode().equals(mode));
//        }
//        
//        // Add a resize listener for the view to activate breadcrumb as needed. 
//        parent.addControlListener(new ControlListener() {
//            public void controlMoved(ControlEvent e) {
//            }
//            public void controlResized(ControlEvent e) {
//                if (parent.isDisposed()) {
//                    return;
//                }
//                if (IDebugPreferenceConstants.DEBUG_VIEW_MODE_AUTO.equals(fCurrentViewMode)) {
//                    autoSelectViewPage(parent);
//                }
//            }
//        });
//	}
//
//	/**
//	 * Copies the view's global action handlers created by createActions(), 
//	 * into the page site's action bars.  This is necessary because the page
//	 * book view resets the view site's global actions after each page switch
//	 * (see bug 264618).
//	 * 
//	 * @param page Page to copy the global actions into.
//	 * 
//	 * @since 3.5
//	 */
//	private void setGlobalActionBarsToPage(IPageBookViewPage page) {
//	    IActionBars pageActionBars = page.getSite().getActionBars();
//        // Set the view site action bars created by createActions() to the 
//        // default page site.
//        IActionBars bars = getViewSite().getActionBars();
//        pageActionBars.setGlobalActionHandler(FIND_ACTION, bars.getGlobalActionHandler(FIND_ACTION));
//        pageActionBars.setGlobalActionHandler(COPY_ACTION, bars.getGlobalActionHandler(COPY_ACTION)); 
//	}
//	
//    /**
//     * Override the default implementation to create the breadcrumb page.
//     * 
//     * @since 3.5
//     * @see #fDefaultPageRec
//     */ 
//	protected PageRec doCreatePage(IWorkbenchPart part) {
////	    if (part instanceof BreadcrumbWorkbenchPart) {
////	        fBreadcrumbPage = new BreadcrumbPage();
////	        fBreadcrumbPage.createControl(getPageBook());
////	        initPage(fBreadcrumbPage);
////	        setGlobalActionBarsToPage(fBreadcrumbPage);
////	        return new PageRec(part, fBreadcrumbPage);
////	    }
//	    return null;
//	}
//	
//    /**
//     * Override the default implementation to create the breadcrumb page.
//     * 
//     * @since 3.5
//     * @see #fDefaultPageRec
//     */ 
//	protected boolean isImportant(IWorkbenchPart part) {
////	    return part instanceof BreadcrumbWorkbenchPart;
//		return false;
//	}
//
//    /**
//     * Override the default implementation to gain access at the default
//     * page record.
//     * 
//     * @since 3.5
//     * @see #fDefaultPageRec
//     */ 
//	protected void showPageRec(PageRec pageRec) {
//	    if (pageRec.page == getDefaultPage()) {
//	        fDefaultPageRec = pageRec;
//	    }
//	        
//	    super.showPageRec(pageRec);
//	}	    
//
//	/**
//	 * Creates actions for controlling view mode.
//	 * 
//	 * @param parent The view's parent control used to calculate view size
//     * in auto mode.
//	 */
//    private void createViewModeActions(final Composite parent) {
//        IActionBars actionBars = getViewSite().getActionBars();
//        IMenuManager viewMenu = actionBars.getMenuManager();
//        
//        fDebugViewModeActions = new DebugViewModeAction[3];
//        fDebugViewModeActions[0] = new DebugViewModeAction(this, IDebugPreferenceConstants.DEBUG_VIEW_MODE_AUTO, parent);
//        fDebugViewModeActions[1] = new DebugViewModeAction(this, IDebugPreferenceConstants.DEBUG_VIEW_MODE_FULL, parent);
//        fDebugViewModeActions[2] = new DebugViewModeAction(this, IDebugPreferenceConstants.DEBUG_VIEW_MODE_COMPACT, parent);
////        fBreadcrumbDropDownAutoExpandAction = new BreadcrumbDropDownAutoExpandAction(this);
//        
//        viewMenu.add(new Separator());
//        final MenuManager modeSubmenu = new MenuManager(LaunchViewMessages.LaunchView_ViewModeMenu_label);
//        modeSubmenu.setRemoveAllWhenShown(true);
//        modeSubmenu.add(fDebugViewModeActions[0]);
//        modeSubmenu.add(fDebugViewModeActions[1]);
//        modeSubmenu.add(fDebugViewModeActions[2]);
//        modeSubmenu.add(new Separator());
////        modeSubmenu.add(fBreadcrumbDropDownAutoExpandAction);
//        viewMenu.add(modeSubmenu);
//        viewMenu.add(new Separator());
//        
//        modeSubmenu.addMenuListener(new IMenuListener() {
//            public void menuAboutToShow(IMenuManager manager) {
//                modeSubmenu.add(fDebugViewModeActions[0]);
//                modeSubmenu.add(fDebugViewModeActions[1]);
//                modeSubmenu.add(fDebugViewModeActions[2]);
//                modeSubmenu.add(new Separator());
////                modeSubmenu.add(fBreadcrumbDropDownAutoExpandAction);
//           }
//        });
//        
//    }
//
// 
//    /**
//     * Sets the current view mode.  If needed, the active view page is changed. 
//     * 
//     * @param mode New mode to set.
//     * @param parent The view's parent control used to calculate view size
//     * in auto mode.
//     * 
//     * @since 3.5
//     */
//    void setViewMode(String mode, Composite parent) {
//        if (fCurrentViewMode.equals(mode)) {
//            return;
//        }
//        
//        fCurrentViewMode = mode;
//        if (IDebugPreferenceConstants.DEBUG_VIEW_MODE_COMPACT.equals(mode)) {
//            showBreadcrumbPage();
//        } else if (IDebugPreferenceConstants.DEBUG_VIEW_MODE_FULL.equals(mode)) {
//            showTreeViewerPage();
//        } else {
//            autoSelectViewPage(parent);
//        }
//        DebugUIPlugin.getDefault().getPreferenceStore().setValue(IDebugPreferenceConstants.DEBUG_VIEW_MODE, mode);
//    }
//
//   /**
//    * Based on the current view size, select the active view page 
//    * (tree viewer vs. breadcrumb).
//    * 
//    * @param parent View pane control.
//    * 
//    * @since 3.5
//    */
//   private void autoSelectViewPage(Composite parent) {
////       int breadcrumbHeight = fBreadcrumbPage.getHeight();
////       if (parent.getClientArea().height < breadcrumbHeight + BREADCRUMB_TRIGGER_RANGE) {
////           showBreadcrumbPage();
////       } 
////       else if (parent.getClientArea().height > breadcrumbHeight + BREADCRUMB_STICKY_RANGE) 
////       {
//           showTreeViewerPage();
////       }
//   }
//   
//
//    /**
//     * Shows the tree viewer in the Debug view.
//     * 
//     * @since 3.5
//     */
//	void showTreeViewerPage() {
//	    if (fDefaultPageRec != null && !getDefaultPage().equals(getCurrentPage())) {
//            showPageRec(fDefaultPageRec);
//            fContextProviderProxy.setActiveProvider(fTreeViewerDebugContextProvider);
//            // Clear the selection in the breadcrumb to avoid having it re-selected
//            // when the breadcrumb is activated again (bug 268124).
////            fBreadcrumbPage.fCrumb.clearSelection();
//	    }
//	}
//
//	/**
// 	 * Shows the breadcrumb in the Debug view.
// 	 * 
// 	 * @since 3.5
//	 */
//	void showBreadcrumbPage() {
////        PageRec rec = getPageRec(fBreadcrumbPage);
////        if (rec != null && !fBreadcrumbPage.equals(getCurrentPage())) {
////            showPageRec(rec);
////            // Ask the breadcrumb to take focus if we're the active part.
////            if (getSite().getPage().getActivePart() == this) {
////                setFocus();
////            }
////            fBreadcrumbPage.fCrumb.debugContextChanged(new DebugContextEvent(
////                fTreeViewerDebugContextProvider, 
////                fTreeViewerDebugContextProvider.getActiveContext(), 
////                DebugContextEvent.ACTIVATED));
////            fContextProviderProxy.setActiveProvider(fBreadcrumbPage.getContextProvider());
////        }
//	}
//	
//	/* (non-Javadoc)
//	 * @see org.eclipse.debug.ui.AbstractDebugView#createViewer(org.eclipse.swt.widgets.Composite)
//	 */
//	protected Viewer createViewer(Composite parent) {
//		fPresentation = new DelegatingModelPresentation();
//		TreeModelViewer viewer = new TreeModelViewer(parent,
//				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.VIRTUAL,
//				new DebugModelPresentationContext(IDebugUIConstants.ID_DEBUG_VIEW, fPresentation));
//        
//        viewer.addSelectionChangedListener(fTreeViewerSelectionChangedListener);
//        viewer.getControl().addKeyListener(new KeyAdapter() {
//        	public void keyPressed(KeyEvent event) {
//        		if (event.character == SWT.DEL && event.stateMask == 0) {
//        			handleDeleteKeyPressed();
//        		}
//        	}
//        });
//        viewer.addViewerUpdateListener(this);        
//        
//		viewer.setInput(DebugPlugin.getDefault().getLaunchManager());
//		//setEventHandler(new LaunchViewEventHandler(this));
//		fTreeViewerDebugContextProvider = new TreeViewerContextProvider(viewer);
//		
//		return viewer;
//	}
//		
//	private void handleDeleteKeyPressed() {
//		IStructuredSelection selection= (IStructuredSelection) getViewer().getSelection();
//		Iterator iter= selection.iterator();
//		Object item;
//		boolean itemsToTerminate= false;
//		ITerminate terminable;
//		while (iter.hasNext()) {
//			item= iter.next();
//			if (item instanceof ITerminate) {
//				terminable= (ITerminate) item;
//				if (terminable.canTerminate() && !terminable.isTerminated()) {
//					itemsToTerminate= true;
//					break;
//				}
//			}
//		}
//		if (itemsToTerminate) {
//			// Prompt the user to proceed with termination
//			if (!MessageDialog.openQuestion(getSite().getShell(), DebugUIViewsMessages.LaunchView_Terminate_and_Remove_1, DebugUIViewsMessages.LaunchView_Terminate_and_remove_selected__2)) {  
//				return;
//			}
//		}
//		MultiStatus status= new MultiStatus(DebugUIPlugin.getUniqueIdentifier(), DebugException.REQUEST_FAILED, DebugUIViewsMessages.LaunchView_Exceptions_occurred_attempting_to_terminate_and_remove_3, null); 
//		iter= selection.iterator(); 
//		while (iter.hasNext()) {
//			try {
//				terminateAndRemove(iter.next());
//			} catch (DebugException exception) {
//				status.merge(exception.getStatus());				
//			}
//		}
//		if (!status.isOK()) {
//			IWorkbenchWindow window= DebugUIPlugin.getActiveWorkbenchWindow();
//			if (window != null) {
//				DebugUIPlugin.errorDialog(window.getShell(), DebugUIViewsMessages.LaunchView_Terminate_and_Remove_4, DebugUIViewsMessages.LaunchView_Terminate_and_remove_failed_5, status);  
//			} else {
//				DebugUIPlugin.log(status);
//			}
//		}
//	}
//	
//	/**
//	 * Terminates and removes the given element from the launch manager
//	 */
//	public static void terminateAndRemove(Object element) throws DebugException {
//		ILaunch launch= null;
//		ITerminate terminable = null;
//		if (element instanceof ILaunch) {
//			launch= (ILaunch) element;
//		} else if (element instanceof IDebugElement) {
//			launch= ((IDebugElement) element).getLaunch();
//		} else if (element instanceof IProcess) {
//			launch= ((IProcess) element).getLaunch();
//		}		
//		terminable = launch;
//		if (terminable == null) {
//			if (element instanceof ITerminate) {
//				terminable = (ITerminate) element;
//			}
//		}
//		if (terminable == null) {
//			return;
//		}
//		if (!(terminable.canTerminate() || terminable.isTerminated())) {
//			// Don't try to terminate or remove attached launches
//			return;
//		}
//		try {
//			if (!terminable.isTerminated()) {
//				terminable.terminate();
//			}
//		} finally {
//			if (launch != null) {
//				ILaunchManager lManager= DebugPlugin.getDefault().getLaunchManager();
//				lManager.removeLaunch(launch);		
//			}
//		}
//	}
//
//	private void commonInit(IViewSite site) {
//		site.getPage().addPartListener((IPartListener2) this);
//		site.getWorkbenchWindow().addPageListener(this);
//		site.getWorkbenchWindow().addPerspectiveListener(this);
//	}
//	
//	private void preferenceInit(IViewSite site) {
//        PREF_STATE_MEMENTO = PREF_STATE_MEMENTO + site.getId();
//        IPreferenceStore store = DebugUIPlugin.getDefault().getPreferenceStore();
//        String string = store.getString(PREF_STATE_MEMENTO);
//        if(string.length() > 0) {
//            ByteArrayInputStream bin = new ByteArrayInputStream(string.getBytes());
//            InputStreamReader reader = new InputStreamReader(bin);
//            try {
//                XMLMemento stateMemento = XMLMemento.createReadRoot(reader);
//                setMemento(stateMemento);
//            } catch (WorkbenchException e) {
//            } finally {
//                try {
//                    reader.close();
//                    bin.close();
//                } catch (IOException e){}
//            }
//        }
//        IMemento mem = getMemento();
//
//        if (mem != null) {
//            Boolean auto = mem.getBoolean(BREADCRUMB_DROPDOWN_AUTO_EXPAND);
//            if(auto != null) {
//                setBreadcrumbDropDownAutoExpand(auto.booleanValue());
//            } 
//        }
//	}
//	
//	/* (non-Javadoc)
//	 * @see org.eclipse.ui.IViewPart#init(org.eclipse.ui.IViewSite)
//	 */
//	public void init(IViewSite site) throws PartInitException {
//		super.init(site);
//		commonInit(site);
//		preferenceInit(site);
//	}
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.ui.IViewPart#init(org.eclipse.ui.IViewSite, org.eclipse.ui.IMemento)
//	 */
//	public void init(IViewSite site, IMemento memento) throws PartInitException {
//		super.init(site, memento);
//		commonInit(site);
//        preferenceInit(site);
//	}
//
//    /* (non-Javadoc)
//     * @see org.eclipse.ui.part.PageBookView#partDeactivated(org.eclipse.ui.IWorkbenchPart)
//     */
//    public void partDeactivated(IWorkbenchPart part) {
//        String id = part.getSite().getId();
//        if (id.equals(getSite().getId())) {
//            ByteArrayOutputStream bout = new ByteArrayOutputStream();
//            OutputStreamWriter writer = new OutputStreamWriter(bout);
//
//            try {
//                XMLMemento memento = XMLMemento.createWriteRoot("DebugViewMemento"); //$NON-NLS-1$
//                saveViewerState(memento);
//                memento.save(writer);
//
//                IPreferenceStore store = DebugUIPlugin.getDefault().getPreferenceStore();
//                String xmlString = bout.toString();
//                store.putValue(PREF_STATE_MEMENTO, xmlString);
//            } catch (IOException e) {
//            } finally {
//                try {
//                    writer.close();
//                    bout.close();
//                } catch (IOException e) {
//                }
//            }
//        }
//        super.partDeactivated(part);
//    }
//
//    /**
//     * Saves the current state of the viewer
//     * @param memento the memento to write the viewer state into
//     */
//    public void saveViewerState(IMemento memento) {
//        memento.putBoolean(BREADCRUMB_DROPDOWN_AUTO_EXPAND, getBreadcrumbDropDownAutoExpand());
//    }
//	
//	/* (non-Javadoc)
//	 * @see org.eclipse.debug.ui.AbstractDebugView#configureToolBar(org.eclipse.jface.action.IToolBarManager)
//	 */
//	protected void configureToolBar(IToolBarManager tbm) {
//		tbm.add(new Separator(IDebugUIConstants.THREAD_GROUP));
//		tbm.add(new Separator(IDebugUIConstants.STEP_GROUP));
//		tbm.add(new GroupMarker(IDebugUIConstants.STEP_INTO_GROUP));
//		tbm.add(new GroupMarker(IDebugUIConstants.STEP_OVER_GROUP));
//		tbm.add(new GroupMarker(IDebugUIConstants.STEP_RETURN_GROUP));
//		tbm.add(new GroupMarker(IDebugUIConstants.EMPTY_STEP_GROUP));
//		tbm.add(new Separator(IDebugUIConstants.RENDER_GROUP));
//        
//        tbm.appendToGroup(IDebugUIConstants.THREAD_GROUP, getAction(RESUME));
//        tbm.appendToGroup(IDebugUIConstants.THREAD_GROUP, getAction(SUSPEND));
//        tbm.appendToGroup(IDebugUIConstants.THREAD_GROUP, getAction(TERMINATE));
//        tbm.appendToGroup(IDebugUIConstants.THREAD_GROUP, getAction(DISCONNECT));
//        
//        tbm.appendToGroup(IDebugUIConstants.STEP_INTO_GROUP, getAction(STEP_INTO));
//        tbm.appendToGroup(IDebugUIConstants.STEP_OVER_GROUP, getAction(STEP_OVER));
//        tbm.appendToGroup(IDebugUIConstants.STEP_RETURN_GROUP, getAction(STEP_RETURN));
//        
//        tbm.appendToGroup(IDebugUIConstants.EMPTY_STEP_GROUP, getAction(DROP_TO_FRAME));
//        
//        tbm.appendToGroup(IDebugUIConstants.RENDER_GROUP, getAction(TOGGLE_STEP_FILTERS));
//	}	
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.ui.IWorkbenchPart#dispose()
//	 */
//	public void dispose() {
//	    getSite().getSelectionProvider().removeSelectionChangedListener(this);
//		DebugUITools.getDebugContextManager().getContextService(getSite().getWorkbenchWindow()).removeDebugContextProvider(fContextProviderProxy);
//        fContextProviderProxy.dispose();
//        fTreeViewerDebugContextProvider.dispose();
//        disposeActions();
//	    Viewer viewer = getViewer();
//		if (viewer != null) {
//			viewer.removeSelectionChangedListener(fTreeViewerSelectionChangedListener);
//            ((TreeModelViewer)viewer).removeViewerUpdateListener(this);
//		}
//		IWorkbenchPage page = getSite().getPage();
//		page.removePartListener((IPartListener2) this);
//		IWorkbenchWindow window = getSite().getWorkbenchWindow();
//		window.removePerspectiveListener(this);
//		window.removePageListener(this);
//		
//		super.dispose();
//	}
//		
//	private void disposeActions() {
//        PropertyDialogAction properties = (PropertyDialogAction) getAction("Properties"); //$NON-NLS-1$
//        properties.dispose();
//        
//        disposeCommandAction(TERMINATE);
//        disposeCommandAction(DISCONNECT);
//        disposeCommandAction(SUSPEND);
//        disposeCommandAction(RESUME);
//        disposeCommandAction(STEP_RETURN);
//        disposeCommandAction(STEP_OVER);
//        disposeCommandAction(STEP_INTO);
//        disposeCommandAction(DROP_TO_FRAME);
//        disposeCommandAction(TERMINATE_AND_REMOVE);
//        disposeCommandAction(TERMINATE_AND_RELAUNCH);
//        disposeCommandAction(TERMINATE_ALL);
//    }
//
//    /**
//	 * The selection has changed in the viewer. Show the
//	 * associated source code if it is a stack frame.
//	 * 
//	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
//	 */
//	public void selectionChanged(SelectionChangedEvent event) {
//		updateObjects();
//	}
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
//	 */
//	public void doubleClick(DoubleClickEvent event) {
//		ISelection selection= event.getSelection();
//		if (!(selection instanceof IStructuredSelection)) {
//			return;
//		}
//		IStructuredSelection ss= (IStructuredSelection)selection;
//		Object o= ss.getFirstElement();
//		if (o == null || o instanceof IStackFrame) {
//			return;
//		} 
//		StructuredViewer viewer = (StructuredViewer) getViewer();
//		viewer.refresh(o);
//	}
//		
//	/* (non-Javadoc)
//	 * @see org.eclipse.ui.IPerspectiveListener#perspectiveActivated(org.eclipse.ui.IWorkbenchPage, org.eclipse.ui.IPerspectiveDescriptor)
//	 */
//	public void perspectiveActivated(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
//		setActive(page.findView(getSite().getId()) != null);
//		updateObjects();
//	}
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.ui.IPerspectiveListener#perspectiveChanged(org.eclipse.ui.IWorkbenchPage, org.eclipse.ui.IPerspectiveDescriptor, java.lang.String)
//	 */
//	public void perspectiveChanged(IWorkbenchPage page, IPerspectiveDescriptor perspective, String changeId) {
//		setActive(page.findView(getSite().getId()) != null);
//	}
//	
//	/* (non-Javadoc)
//	 * @see org.eclipse.ui.IPerspectiveListener2#perspectiveChanged(org.eclipse.ui.IWorkbenchPage, org.eclipse.ui.IPerspectiveDescriptor, org.eclipse.ui.IWorkbenchPartReference, java.lang.String)
//	 */
//	public void perspectiveChanged(IWorkbenchPage page, IPerspectiveDescriptor perspective, IWorkbenchPartReference partRef, String changeId) {
//	}
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.ui.IPageListener#pageActivated(org.eclipse.ui.IWorkbenchPage)
//	 */
//	public void pageActivated(IWorkbenchPage page) {
//		if (getSite().getPage().equals(page)) {
//			setActive(true);
//			updateObjects();
//		}
//	}
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.ui.IPageListener#pageClosed(org.eclipse.ui.IWorkbenchPage)
//	 */
//	public void pageClosed(IWorkbenchPage page) {
//	}
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.ui.IPageListener#pageOpened(org.eclipse.ui.IWorkbenchPage)
//	 */
//	public void pageOpened(IWorkbenchPage page) {
//	}
//	
//	/* (non-Javadoc)
//	 * @see org.eclipse.debug.ui.IDebugView#getPresentation(java.lang.String)
//	 */
//	public IDebugModelPresentation getPresentation(String id) {
//		return ((DelegatingModelPresentation)fPresentation).getPresentation(id);
//	}
//	
//	/* (non-Javadoc)
//	 * @see org.eclipse.debug.ui.AbstractDebugView#fillContextMenu(org.eclipse.jface.action.IMenuManager)
//	 */
//	protected void fillContextMenu(IMenuManager menu) {
//		
//		menu.add(new Separator(IDebugUIConstants.EMPTY_EDIT_GROUP));
//		menu.add(new Separator(IDebugUIConstants.EDIT_GROUP));
//		menu.add(getAction(FIND_ACTION));
//		menu.add(new Separator(IDebugUIConstants.EMPTY_STEP_GROUP));
//		menu.add(new Separator(IDebugUIConstants.STEP_GROUP));
//		menu.add(new GroupMarker(IDebugUIConstants.STEP_INTO_GROUP));
//		menu.add(new GroupMarker(IDebugUIConstants.STEP_OVER_GROUP));
//		menu.add(new GroupMarker(IDebugUIConstants.STEP_RETURN_GROUP));
//		menu.add(new Separator(IDebugUIConstants.RENDER_GROUP));
//		menu.add(new Separator(IDebugUIConstants.EMPTY_THREAD_GROUP));
//		menu.add(new Separator(IDebugUIConstants.THREAD_GROUP));
//		menu.add(new Separator(IDebugUIConstants.EMPTY_LAUNCH_GROUP));
//		menu.add(new Separator(IDebugUIConstants.LAUNCH_GROUP));
//		IStructuredSelection selection = (IStructuredSelection) getSite().getSelectionProvider().getSelection();
//		updateAndAdd(menu, fEditConfigAction, selection);
//		updateAndAdd(menu, fAddToFavoritesAction, selection);
//		updateAndAdd(menu, fEditSourceAction, selection);
//		updateAndAdd(menu, fLookupAction, selection);
//		menu.add(new Separator(IDebugUIConstants.EMPTY_RENDER_GROUP));
//		menu.add(new Separator(IDebugUIConstants.RENDER_GROUP));
//		menu.add(new Separator(IDebugUIConstants.PROPERTY_GROUP));
//		PropertyDialogAction action = (PropertyDialogAction)getAction("Properties"); //$NON-NLS-1$
//		/**
//		 * TODO hack to get around bug 148424, remove if UI ever fixes the PropertyDialogAction to respect enablesWhen conditions
//		 */
//		TreeSelection sel = (TreeSelection) fTreeViewerDebugContextProvider.getActiveContext();
//		boolean enabled = true;
//		if(sel != null && sel.size() > 0) {
//			enabled = !(sel.getFirstElement() instanceof ILaunch);
//		}
//		action.setEnabled(action.isApplicableForSelection() && enabled);
//		menu.add(action);
//		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
//        
//        menu.appendToGroup(IDebugUIConstants.LAUNCH_GROUP, getAction(TERMINATE_AND_REMOVE));
//        menu.appendToGroup(IDebugUIConstants.LAUNCH_GROUP, getAction(TERMINATE_ALL));
//		
//        menu.appendToGroup(IDebugUIConstants.THREAD_GROUP, getAction(RESUME));
//        menu.appendToGroup(IDebugUIConstants.THREAD_GROUP, getAction(SUSPEND));
//        menu.appendToGroup(IDebugUIConstants.THREAD_GROUP, getAction(TERMINATE));
//        menu.appendToGroup(IDebugUIConstants.THREAD_GROUP, getAction(TERMINATE_AND_RELAUNCH));
//        menu.appendToGroup(IDebugUIConstants.THREAD_GROUP, getAction(DISCONNECT));
//        
//        menu.appendToGroup(IDebugUIConstants.STEP_INTO_GROUP, getAction(STEP_INTO));
//        menu.appendToGroup(IDebugUIConstants.STEP_OVER_GROUP, getAction(STEP_OVER));
//        menu.appendToGroup(IDebugUIConstants.STEP_RETURN_GROUP, getAction(STEP_RETURN));
//        
//        menu.appendToGroup(IDebugUIConstants.EMPTY_STEP_GROUP, getAction(DROP_TO_FRAME));
//        
//        menu.appendToGroup(IDebugUIConstants.RENDER_GROUP, getAction(TOGGLE_STEP_FILTERS));
//    }
//	
//	/**
//	 * Updates the enabled state of the given action based on the selection
//	 * and adds to the menu if enabled.
//	 * 
//	 * @param menu menu to add the action to
//	 * @param action action to add if enabled
//	 * @param selection selection to update enabled state for
//	 */
//	private void updateAndAdd(IMenuManager menu, SelectionListenerAction action, IStructuredSelection selection) {
//		action.selectionChanged(selection);
//		if (action.isEnabled()) {
//			menu.add(action);
//		}		
//	}
//		
//	/**
//	 * Sets whether this view is in the active page of a
//	 * perspective. Since a page can have more than one
//	 * perspective, this view only show's source when in
//	 * the active perspective/page.
//	 * 
//	 * @param active whether this view is in the active page of a
//	 * perspective
//	 */
//	protected void setActive(boolean active) {
//		fIsActive = active;
//	} 
//
//	/**
//	 * Returns whether this view is in the active page of
//	 * the active perspective and has been fully created.
//	 * 
//	 * @return whether this view is in the active page of
//	 * the active perspective and has been fully created.
//	 */
//	protected boolean isActive() {
//		return fIsActive && getViewer() != null;
//	}
//	
//	/* (non-Javadoc)
//	 * @see org.eclipse.ui.part.IShowInTarget#show(org.eclipse.ui.part.ShowInContext)
//	 */
//	public boolean show(ShowInContext context) {
//		ISelection selection = context.getSelection();
//		if (selection != null) {
//			if (selection instanceof IStructuredSelection) {
//				IStructuredSelection ss = (IStructuredSelection)selection;
//				if (ss.size() == 1) {
//					Object obj = ss.getFirstElement();
//					if (obj instanceof IDebugTarget || obj instanceof IProcess) {
//						Viewer viewer = getViewer();
//						if (viewer instanceof InternalTreeModelViewer) {
//							InternalTreeModelViewer tv = (InternalTreeModelViewer) viewer;
//							tv.setSelection(selection, true, true);
//						} else {
//							viewer.setSelection(selection, true);
//						}
//						return true;
//					}
//				}
//			}
//		}
//		return false;
//	}
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.ui.part.IShowInSource#getShowInContext()
//	 */
//	public ShowInContext getShowInContext() {
//		if (isActive()) { 
//			IStructuredSelection selection = (IStructuredSelection)getViewer().getSelection();
//			if (selection.size() == 1) { 
//				Object object = selection.getFirstElement();
//				if (object instanceof IAdaptable) {
//					IAdaptable adaptable = (IAdaptable) object;
//					IShowInSource show = (IShowInSource) adaptable.getAdapter(IShowInSource.class);
//					if (show != null) {
//						return show.getShowInContext();
//					}
//				}
//			}
//		}
//		return null;
//	}
//	
//	/* (non-Javadoc)
//	 * @see org.eclipse.ui.part.IShowInTargetList#getShowInTargetIds()
//	 */
//	public String[] getShowInTargetIds() {
//		if (isActive()) { 
//			IStructuredSelection selection = (IStructuredSelection)getViewer().getSelection();
//			if (selection.size() == 1) { 
//				Object object = selection.getFirstElement();
//				if (object instanceof IAdaptable) {
//					IAdaptable adaptable = (IAdaptable) object;
//					IShowInTargetList show = (IShowInTargetList) adaptable.getAdapter(IShowInTargetList.class);
//					if (show != null) {
//						return show.getShowInTargetIds();
//					}
//				}
//			}
//		}
//		return new String[0];
//	}
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.ui.IPartListener2#partClosed(org.eclipse.ui.IWorkbenchPartReference)
//	 */
//	public void partClosed(IWorkbenchPartReference partRef) {
//	}
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.ui.IPartListener2#partVisible(org.eclipse.ui.IWorkbenchPartReference)
//	 */
//	public void partVisible(IWorkbenchPartReference partRef) {
//		IWorkbenchPart part = partRef.getPart(false);
//		if (part == this) {
//			setActive(true);
//// TODO: Workaround for Bug #63332. Reexamine after M9.
////			updateContextListener();
//			// When the launch view becomes visible, turn on the
//			// debug action set. Note that the workbench will handle the
//			// case where the user really doesn't want the action set
//			// enabled - showActionSet(String) will do nothing for an
//			// action set that's been manually disabled.
//			getSite().getPage().showActionSet(IDebugUIConstants.DEBUG_ACTION_SET);
//		}
//	}
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.ui.IPartListener2#partOpened(org.eclipse.ui.IWorkbenchPartReference)
//	 */
//	public void partOpened(IWorkbenchPartReference partRef) {
//	}
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.ui.IPartListener2#partActivated(org.eclipse.ui.IWorkbenchPartReference)
//	 */
//	public void partActivated(IWorkbenchPartReference partRef) {
//	}
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.ui.IPartListener2#partBroughtToTop(org.eclipse.ui.IWorkbenchPartReference)
//	 */
//	public void partBroughtToTop(IWorkbenchPartReference partRef) {
//	}
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.ui.IPartListener2#partDeactivated(org.eclipse.ui.IWorkbenchPartReference)
//	 */
//	public void partDeactivated(IWorkbenchPartReference partRef) {
//	}
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.ui.IPartListener2#partHidden(org.eclipse.ui.IWorkbenchPartReference)
//	 */
//	public void partHidden(IWorkbenchPartReference partRef) {
//	}
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.ui.IPartListener2#partInputChanged(org.eclipse.ui.IWorkbenchPartReference)
//	 */
//	public void partInputChanged(IWorkbenchPartReference partRef) {
//	}
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.debug.ui.AbstractDebugView#becomesVisible()
//	 */
//	protected void becomesVisible() {
//		super.becomesVisible();
//		getViewer().refresh();
//	}
//	
//    /* (non-Javadoc)
//     * @see org.eclipse.debug.internal.ui.viewers.model.provisional.viewers.IViewerUpdateListener#updateComplete(org.eclipse.debug.internal.ui.viewers.provisional.IAsynchronousRequestMonitor)
//     */
//    public void updateComplete(IViewerUpdate update) {
//    }
//
//    /* (non-Javadoc)
//     * @see org.eclipse.debug.internal.ui.viewers.model.provisional.viewers.IViewerUpdateListener#updateStarted(org.eclipse.debug.internal.ui.viewers.provisional.IAsynchronousRequestMonitor)
//     */
//    public void updateStarted(IViewerUpdate update) {
//    }
//
//    /* (non-Javadoc)
//     * @see org.eclipse.debug.internal.ui.viewers.model.provisional.viewers.IViewerUpdateListener#viewerUpdatesBegin()
//     */
//    public synchronized void viewerUpdatesBegin() {
//        IWorkbenchSiteProgressService progressService = 
//            (IWorkbenchSiteProgressService)getSite().getAdapter(IWorkbenchSiteProgressService.class);
//        if (progressService != null) {
//            progressService.incrementBusy();
//        }
//    }
//
//    /* (non-Javadoc)
//     * @see org.eclipse.debug.internal.ui.viewers.model.provisional.viewers.IViewerUpdateListener#viewerUpdatesComplete()
//     */
//    public synchronized void viewerUpdatesComplete() {
//        IWorkbenchSiteProgressService progressService = 
//            (IWorkbenchSiteProgressService)getSite().getAdapter(IWorkbenchSiteProgressService.class);
//        if (progressService != null) {
//            progressService.decrementBusy();
//        }       
//    }   
//	
//    /**
//     * Returns whether the breadcrumb viewer is currently visible in the view.
//     * 
//     * @since 3.5
//     */
//    boolean isBreadcrumbVisible() {
//        return false;//fBreadcrumbPage.equals(getCurrentPage());
//    }
//    
//    /**
//     * Returns whether the elements in breadcrumb's drop-down viewer should be 
//     * automatically expanded.
//     * 
//     * @since 3.5
//     */
//    boolean getBreadcrumbDropDownAutoExpand() {
//        return fBreadcrumbDropDownAutoExpand;
//    }
//    
//    /**
//     * Sets whether the elements in breadcrumb's drop-down viewer should be 
//     * automatically expanded.
//     * 
//     * @since 3.5
//     */
//    void setBreadcrumbDropDownAutoExpand(boolean expand) {
//        fBreadcrumbDropDownAutoExpand = expand;
//    }
//
//}
////	class VdmTreeViewerContextProvider extends LaunchView.TreeViewerContextProvider
////			
////	{
////
////		private ISelection fContext = null;
////		private TreeModelViewer fViewer = null;
////		private Visitor fVisitor = new Visitor();
////
////		class Visitor implements IModelDeltaVisitor
////		{
////			public boolean visit(IModelDelta delta, int depth)
////			{
////				if ((delta.getFlags() & (IModelDelta.STATE | IModelDelta.CONTENT)) > 0)
////				{
////					// state and/or content change
////					if ((delta.getFlags() & IModelDelta.SELECT) == 0)
////					{
////						// no select flag
////						if ((delta.getFlags() & IModelDelta.CONTENT) > 0)
////						{
////							// content has changed without select >> possible re-activation
////							possibleChange(getViewerTreePath(delta), DebugContextEvent.ACTIVATED);
////						} else if ((delta.getFlags() & IModelDelta.STATE) > 0)
////						{
////							// state has changed without select >> possible state change of active context
////							possibleChange(getViewerTreePath(delta), DebugContextEvent.STATE);
////						}
////					}
////				}
////				return true;
////			}
////		}
////
////		/**
////		 * Returns a tree path for the node, *not* including the root element.
////		 * 
////		 * @param node
////		 *            model delta
////		 * @return corresponding tree path
////		 */
////		private TreePath getViewerTreePath(IModelDelta node)
////		{
////			ArrayList list = new ArrayList();
////			IModelDelta parentDelta = node.getParentDelta();
////			while (parentDelta != null)
////			{
////				list.add(0, node.getElement());
////				node = parentDelta;
////				parentDelta = node.getParentDelta();
////			}
////			return new TreePath(list.toArray());
////		}
////
////		public TreeViewerContextProvider(TreeModelViewer viewer)
////		{
////			super(VdmLaunchView.this);
////			fViewer = viewer;
////			fViewer.addModelChangedListener(this);
////		}
////
////		protected void dispose()
////		{
////			fContext = null;
////			fViewer.removeModelChangedListener(this);
////		}
////
////		/*
////		 * (non-Javadoc)
////		 * @see org.eclipse.debug.ui.contexts.IDebugContextProvider#getActiveContext()
////		 */
////		public synchronized ISelection getActiveContext()
////		{
////			return fContext;
////		}
////
////		protected void activate(ISelection selection)
////		{
////			synchronized (this)
////			{
////				fContext = selection;
////			}
////			fire(new DebugContextEvent(this, selection, DebugContextEvent.ACTIVATED));
////		}
////
////		protected void possibleChange(TreePath element, int type)
////		{
////			DebugContextEvent event = null;
////			synchronized (this)
////			{
////				if (fContext instanceof ITreeSelection)
////				{
////					ITreeSelection ss = (ITreeSelection) fContext;
////					if (ss.size() == 1)
////					{
////						TreePath current = ss.getPaths()[0];
////						if (current.startsWith(element, null))
////						{
////							if (current.getSegmentCount() == element.getSegmentCount())
////							{
////								event = new DebugContextEvent(this, fContext, type);
////							} else
////							{
////								// if parent of the currently selected element
////								// changes, issue event to update STATE only
////								event = new DebugContextEvent(this, fContext, DebugContextEvent.STATE);
////							}
////						}
////					}
////				}
////			}
////			if (event == null)
////			{
////				return;
////			}
////			if (getControl().getDisplay().getThread() == Thread.currentThread())
////			{
////				fire(event);
////			} else
////			{
////				final DebugContextEvent finalEvent = event;
////				Job job = new UIJob("context change") { //$NON-NLS-1$
////					public IStatus runInUIThread(IProgressMonitor monitor)
////					{
////						// verify selection is still the same context since job was scheduled
////						synchronized (TreeViewerContextProvider.this)
////						{
////							if (fContext instanceof IStructuredSelection)
////							{
////								IStructuredSelection ss = (IStructuredSelection) fContext;
////								Object changed = ((IStructuredSelection) finalEvent.getContext()).getFirstElement();
////								if (!(ss.size() == 1 && ss.getFirstElement().equals(changed)))
////								{
////									return Status.OK_STATUS;
////								}
////							}
////						}
////						fire(finalEvent);
////						return Status.OK_STATUS;
////					}
////				};
////				job.setSystem(true);
////				job.schedule();
////			}
////		}
////
////		/*
////		 * (non-Javadoc)
////		 * @see
////		 * org.eclipse.debug.internal.ui.viewers.model.provisional.IModelChangedListener#modelChanged(org.eclipse.debug
////		 * .internal.ui.viewers.model.provisional.IModelDelta)
////		 */
////
////		public void modelChanged(IModelDelta delta, IModelProxy proxy)
////		{
////			delta.accept(fVisitor);
////		}
////
////	}
////
////	protected Viewer createViewer(Composite parent)
////	{
////		super.createViewer(parent);
////		TreeModelViewer viewer = null;
////		try
////		{
////				viewer = new TreeModelViewer(parent, SWT.MULTI
////				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.VIRTUAL, new DebugModelPresentationContext(IDebugUIConstants.ID_DEBUG_VIEW,(IDebugModelPresentation) getPrivateField(LaunchView.class, "fPresentation").get(this))); //fPresentation));
////
////		viewer.addSelectionChangedListener((ISelectionChangedListener) getPrivateField(LaunchView.class, "fTreeViewerSelectionChangedListener").get(this));//fTreeViewerSelectionChangedListener);
////		viewer.getControl().addKeyListener(new KeyAdapter()
////		{
////			public void keyPressed(KeyEvent event)
////			{
////				if (event.character == SWT.DEL && event.stateMask == 0)
////				{
////				try
////				{
////					Method m = LaunchView.class.getDeclaredMethod("handleDeleteKeyPressed", null);
////					m.setAccessible(true);
////					m.invoke(this,null);
////				} catch (IllegalArgumentException e)
////				{
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				} catch (SecurityException e)
////				{
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				} catch (IllegalAccessException e)
////				{
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				} catch (InvocationTargetException e)
////				{
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				} catch (NoSuchMethodException e)
////				{
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}	//handleDeleteKeyPressed();
////				}
////			}
////		});
////		viewer.addViewerUpdateListener(this);
////
////		viewer.setInput(DebugPlugin.getDefault().getLaunchManager());
////
////		//fTreeViewerDebugContextProvider = new TreeViewerContextProvider(viewer);
////		TreeViewerContextProvider	fTreeViewerDebugContextProvider1 = new TreeViewerContextProvider(viewer);
////		
////		
////			getPrivateField(LaunchView.class, "fTreeViewerDebugContextProvider").set(this, fTreeViewerDebugContextProvider1);
////		} catch (IllegalArgumentException e)
////		{
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} catch (SecurityException e)
////		{
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} catch (IllegalAccessException e)
////		{
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} 
////
////		return viewer;
////	}
////	
////	private static Field getPrivateField(Class c, String name)
////	{
////		Field f;
////		try
////		{
////			f = c.getDeclaredField(name);
////			f.setAccessible(true);
////			return f;
////		} catch (SecurityException e)
////		{
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} catch (NoSuchFieldException e)
////		{
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		return null;
////	}
// //}