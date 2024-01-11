package main.mementoPattern;

import java.util.Stack;

public class PageCareTaker {
    private PageMemento currentMemento;
    private final Stack<PageMemento> previousPages = new Stack<>();
    private final Stack<PageMemento> nextPages = new Stack<>();

    /**
     * adds current page
     */
    public void addPageCurrentMemento(final PageMemento memento) {
        if (memento != null) {
            this.setMemento(memento);
            this.previousPages.push(this.currentMemento);
        }
    }

    /**
     * set memento
     */
    public void setMemento(final PageMemento memento) {
        this.currentMemento = memento;
    }

    /**
     * go to previous page
     */
    public void goToPreviousPage() {
        if (this.previousPages.isEmpty()) {
            return;
        }

        this.nextPages.push(this.currentMemento);
        this.currentMemento = this.previousPages.pop();
    }

    /**
     * go to next page
     */
    public void goToNextPage() {
        if (this.nextPages.isEmpty()) {
            return;
        }

        this.previousPages.push(this.currentMemento);
        this.currentMemento = this.nextPages.pop();
    }

    /**
     * get current page
     */
    public PageMemento getCurrentMemento() {
        return this.currentMemento;
    }

    /**
     * are there any previous pages
     */
    public boolean hasPreviousPages() {
        return this.previousPages.isEmpty();
    }

    /**
     * are there any next pages
     */
    public boolean hasNextPages() {
        return this.nextPages.isEmpty();
    }

    /**
     * clear previous pages
     */
    public void clearPreviousPages() {
        this.previousPages.clear();
    }

    /**
     * clear next pages
     */
    public void clearNextPages() {
        this.nextPages.clear();
    }
}
