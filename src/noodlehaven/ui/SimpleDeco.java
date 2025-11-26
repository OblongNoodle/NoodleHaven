package noodlehaven.ui;

import haven.*;

public class SimpleDeco extends Window.DragDeco {
    private final IButton cbtn;
    private Area ca, aa;
    private UI.Grab dm = null;
    private Coord doff;
    private boolean dragsize = true;
    private static final int rszm = UI.scale(7); // Resize margin

    public SimpleDeco(Window parent) {
        try {
            cbtn = add(new IButton(Resource.loadimg("gfx/hud/wnd/cbtn"),
                    Resource.loadimg("gfx/hud/wnd/cbtn"),
                    Resource.loadimg("gfx/hud/wnd/cbtn")))
                    .action(parent::reqclose);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void iresize(Coord isz) {
        int border = UI.scale(2);
        int titleh = UI.scale(22);
        resize(isz.add(border * 2, border * 2 + titleh));
        ca = Area.sized(Coord.of(border, border + titleh), isz);
        aa = ca;
        cbtn.c = Coord.of(sz.x - cbtn.sz.x - border, border + 2);
    }

    public Area contarea() {
        return aa;
    }

    public void draw(GOut g) {
        drawframe(g);
        cdraw(g);
        super.draw(g);
    }

    protected void cdraw(GOut g) {
        ((Window)parent).cdraw(g);
    }

    protected void drawframe(GOut g) {
        // Border
        g.chcolor(0, 0, 0, 255);
        g.rect(Coord.z, sz);
        g.rect(Coord.of(1, 1), sz.sub(2, 2));

        // Title bar - custom brown
        g.chcolor(54, 47, 36, 255);
        g.frect(Coord.of(2, 2), new Coord(sz.x - 4, UI.scale(20)));

        // Window background - custom brown
        g.chcolor(54, 47, 36, 255);
        g.frect(Coord.of(2, UI.scale(22)), new Coord(sz.x - 4, sz.y - UI.scale(24)));
        g.chcolor();

        Window wnd = (Window)parent;
        if(wnd.cap != null) {
            Text.Foundry cf = new Text.Foundry(Text.sans.deriveFont(java.awt.Font.PLAIN), 11);
            Text title = cf.render(wnd.cap, java.awt.Color.WHITE);
            g.image(title.tex(), Coord.of(UI.scale(6), UI.scale(5)));
        }
        Coord br = sz.sub(UI.scale(3), UI.scale(3));
        int size = UI.scale(12);

        g.chcolor(94, 75, 60, 255);
        for(int i = 0; i < size; i++) {
            g.line(new Coord(br.x - i, br.y),
                    new Coord(br.x, br.y - i), 2);
        }

        g.chcolor(50, 40, 30, 255);
        for(int i = 0; i < 3; i++) {
            int offset = i * UI.scale(3) + UI.scale(2);
            g.line(new Coord(br.x - offset, br.y - 1),
                    new Coord(br.x - 1, br.y - offset), 2);
        }

        g.chcolor();
    }

    public boolean checkhit(Coord c) {
        return c.isect(Coord.z, sz);
    }

    public boolean mousedown(MouseDownEvent ev) {
        System.out.println("SimpleDeco mousedown at: " + ev.c + ", sz=" + sz + ", dragsize=" + dragsize);

        if(dragsize && (ev.b == 1)) {
            Coord c = ev.c;

            // Only allow resize from bottom-right corner
            int resizeMargin = UI.scale(18);

            // Check if in BOTTOM-RIGHT corner only
            if((c.x >= (sz.x - resizeMargin)) && (c.y >= (sz.y - resizeMargin))) {
                System.out.println("RESIZE ZONE HIT!");
                Window wnd = (Window)parent;
                wnd.parent.setfocus(wnd);
                wnd.raise();
                dm = ui.grabmouse(this);
                doff = c;
                return true;
            }
        }

        if(super.mousedown(ev))
            return true;

        return false;
    }

    public boolean mouseup(MouseUpEvent ev) {
        if(dm != null) {
            dm.remove();
            dm = null;
            return true;
        }
        return super.mouseup(ev);
    }

    public void mousemove(MouseMoveEvent ev) {
        if(dm != null) {
            Window wnd = (Window)parent;
            Coord c = ev.c;
            Coord delta = c.sub(doff);

            boolean lb = doff.x < rszm;
            boolean rb = doff.x >= (sz.x - rszm);
            boolean tb = doff.y < rszm;
            boolean bb = doff.y >= (sz.y - rszm);

            Coord newsz = wnd.csz();
            Coord newc = wnd.c;

            if(rb) newsz = newsz.add(delta.x, 0);
            if(bb) newsz = newsz.add(0, delta.y);
            if(lb) {
                newsz = newsz.sub(delta.x, 0);
                newc = newc.add(delta.x, 0);
            }
            if(tb) {
                newsz = newsz.sub(0, delta.y);
                newc = newc.add(0, delta.y);
            }

            newsz = newsz.max(UI.scale(100, 100));

            wnd.resize(newsz);
            wnd.c = newc;
            doff = c;
        } else {
            super.mousemove(ev);
        }
    }
}